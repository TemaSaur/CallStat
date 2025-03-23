package com.github.temasaur.callstat.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "records", indexes = {
    @Index(name = "initiator_idx", columnList = "initiator_id"),
    @Index(name = "recipient_idx", columnList = "recipient_id"),
	@Index(name = "call_start_idx", columnList = "call_start") // for sorting
})
public class Record {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
	@JoinColumn(name = "initiator_id")
    public Subscriber initiator;

    @ManyToOne
	@JoinColumn(name = "recipient_id")
    public Subscriber recipient;

	public LocalDateTime callStart;
	public LocalDateTime callEnd;

	public Record() {}

	public Record(
		Subscriber initiator,
		Subscriber recipient,
		LocalDateTime callStart,
		LocalDateTime callEnd
	) {
		this.initiator = initiator;
		this.recipient = recipient;
		this.callStart = callStart;
		this.callEnd = callEnd;
	}
}
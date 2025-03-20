package com.github.temasaur.callstat.models;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "call_data_records")
public class Record {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    public Subscriber initiator;

    @ManyToOne
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
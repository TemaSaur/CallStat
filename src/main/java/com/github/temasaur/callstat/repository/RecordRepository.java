package com.github.temasaur.callstat.repository;

import com.github.temasaur.callstat.models.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface RecordRepository extends CrudRepository<Record, Integer> {

    Iterable<Record> findByInitiator_MsisdnOrRecipient_MsisdnOrderByCallStartAsc(String msisdn1, String msisdn2);

//     Iterable<Record> findByInitiator_MsisdnOrRecipient_MsisdnAndCallStartAfterAndCallEndBefore(
//             String msisdn1, String msisdn2, LocalDateTime start, LocalDateTime end);

    @Query("SELECT R FROM Record R " +
            "WHERE (R.initiator.msisdn = ?1 OR R.recipient.msisdn = ?1) " +
            "AND (R.callStart >= ?2 AND R.callEnd < ?3) " +
            "ORDER BY R.callStart ASC")
    Iterable<Record> findBySubscriberWithin(String msisdn, LocalDateTime start, LocalDateTime end);
}

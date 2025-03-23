package com.github.temasaur.callstat.repository;

import com.github.temasaur.callstat.models.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface RecordRepository extends CrudRepository<Record, Long> {

    Iterable<Record> findByInitiator_MsisdnOrRecipient_MsisdnOrderByCallStartAsc(String msisdn1, String msisdn2);

    @Query("SELECT R FROM Record R " +
            "WHERE (R.initiator.msisdn = ?1 OR R.recipient.msisdn = ?1) " +
            "AND (R.callStart >= ?2 AND R.callEnd < ?3) " +
            "ORDER BY R.callStart ASC")
    Iterable<Record> findBySubscriberWithin(String msisdn, LocalDateTime start, LocalDateTime end);

    // here it is possible to create a query to compute the
    // total duration of calls for a subscriber but the jpql
    // treats SUM as bigdecimal, which means you have to deal
    // with casting types, dealing with precision, etc.
    //
    // if the computation becomes the bottleneck we can create
    // a native query.
    //
    // version 0:
    /*
     * @Query("SELECT SUM(R.callEnd - R.callStart) AS duration FROM Record R " +
     *         "WHERE (R.initiator.msisdn = ?1) " +
     *         "AND (R.callStart >= ?2 AND R.callEnd < ?3)")
     * Duration sumDurationByWithin(String msisdn, LocalDateTime start, LocalDateTime end);
     */
}

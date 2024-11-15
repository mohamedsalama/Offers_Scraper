package com.cib.scraper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "offers")
public class Offers {

    @Id
    @Column(name = "offer_id", length = 50, unique = true, nullable = false)
    private String offerId;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "category")
    private String category;

    @Column(name = "installment")
    private String installment;

    @Column(name = "details")
    private String details;

    @Column(name = "valid_until_str", length = 15)
    private String validUntilStr;

    @Temporal(TemporalType.DATE)
    @Column(name = "valid_until", length = 15)
    private Date validUntil;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", length = 19)
    private Date creationDate;
}

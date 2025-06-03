package com.egov.contractservice;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reservations")
@Getter @Setter
public class Project
{
    @Id
    private String id;
    private String hotelname;
    private String checkinDate;
    private String checkoutDate;
    private String guestcount;
    private Integer room;
    private String status;
}

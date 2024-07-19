package com.example.nbrb;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "currency_rate", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "curAbbreviation"})})
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("Cur_ID")
    private Long curId;

    @JsonProperty("Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @JsonProperty("Cur_Abbreviation")
    private String curAbbreviation;

    @JsonProperty("Cur_Scale")
    private Integer curScale;

    @JsonProperty("Cur_Name")
    private String curName;

    @JsonProperty("Cur_OfficialRate")
    private Double curOfficialRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurId() {
        return curId;
    }

    public void setCurId(Long curId) {
        this.curId = curId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCurAbbreviation() {
        return curAbbreviation;
    }

    public void setCurAbbreviation(String curAbbreviation) {
        this.curAbbreviation = curAbbreviation;
    }

    public Integer getCurScale() {
        return curScale;
    }

    public void setCurScale(Integer curScale) {
        this.curScale = curScale;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public Double getCurOfficialRate() {
        return curOfficialRate;
    }

    public void setCurOfficialRate(Double curOfficialRate) {
        this.curOfficialRate = curOfficialRate;
    }
}

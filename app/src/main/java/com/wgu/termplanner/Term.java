package com.wgu.termplanner;

import java.util.ArrayList;
import java.util.Date;

public class Term {

    public static ArrayList<Term> termArrayList = new ArrayList<>();
    public static String TERM_EDIT_EXTRA = "termEdit";


    private int id;
    private String title;
    private String startDate;
    private String endDate;
    private Date deleted;


    public Term(int id, String title, String startDate, String endDate, Date deleted) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deleted = deleted;
    }

    public Term(int id, String title, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        deleted = null;
    }

    public Term(String title, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Term getTermForID(int passedTermID) {
        for (Term term : termArrayList)
        {
            if(term.getId() == passedTermID)
                return term;
        }
        return null;
    }

    public static ArrayList<Term> nonDeletedTerms() {
        ArrayList<Term> nonDeleted = new ArrayList<>();
        for(Term term : termArrayList)
        {
            if(term.getDeleted() == null)
                nonDeleted.add(term);
        }
        return nonDeleted;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }


}

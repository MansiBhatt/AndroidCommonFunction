package com.integratingdemo.strawberry_logger.model;

/**
 * Pojo file for Strawberry data
 */

public class StrawberryData {

    private int m_strawberrry_id;
    private String m_date;
    private float m_weight;
    private int m_sunlight;
    private float m_compost;
    private float m_water;

    public StrawberryData(int m_strawberrry_id, String m_date, float m_weight, int m_sunlight, float m_compost, float m_water) {
        this.m_strawberrry_id = m_strawberrry_id;
        this.m_date = m_date;
        this.m_weight = m_weight;
        this.m_sunlight = m_sunlight;
        this.m_compost = m_compost;
        this.m_water = m_water;
    }

    public StrawberryData() {

    }

    public int getM_strawberrry_id() {
        return m_strawberrry_id;
    }

    public void setM_strawberrry_id(int m_strawberrry_id) {
        this.m_strawberrry_id = m_strawberrry_id;
    }

    public String getM_date() {
        return m_date;
    }

    public void setM_date(String m_date) {
        this.m_date = m_date;
    }

    public float getM_weight() {
        return m_weight;
    }

    public void setM_weight(float m_weight) {
        this.m_weight = m_weight;
    }

    public int getM_sunlight() {
        return m_sunlight;
    }

    public void setM_sunlight(int m_sunlight) {
        this.m_sunlight = m_sunlight;
    }

    public float getM_compost() {
        return m_compost;
    }

    public void setM_compost(float m_compost) {
        this.m_compost = m_compost;
    }

    public float getM_water() {
        return m_water;
    }

    public void setM_water(float m_water) {
        this.m_water = m_water;
    }

    @Override
    public String toString() {
        String[] strawberryType = m_date.split("_");
        return m_strawberrry_id +
                " " + strawberryType[1] +
                " " + m_weight +
                " " + m_sunlight +
                " " + m_compost +
                " " + m_water;
    }
}

package com.banry.pscm.service.labour;

public class LaborWithBLOBs extends Labor {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column labor.trained_history
     *
     * @mbggenerated
     */
    private String trainedHistory;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column labor.profess_qualify
     *
     * @mbggenerated
     */
    private String professQualify;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column labor.trained_history
     *
     * @return the value of labor.trained_history
     *
     * @mbggenerated
     */
    public String getTrainedHistory() {
        return trainedHistory;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column labor.trained_history
     *
     * @param trainedHistory the value for labor.trained_history
     *
     * @mbggenerated
     */
    public void setTrainedHistory(String trainedHistory) {
        this.trainedHistory = trainedHistory == null ? null : trainedHistory.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column labor.profess_qualify
     *
     * @return the value of labor.profess_qualify
     *
     * @mbggenerated
     */
    public String getProfessQualify() {
        return professQualify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column labor.profess_qualify
     *
     * @param professQualify the value for labor.profess_qualify
     *
     * @mbggenerated
     */
    public void setProfessQualify(String professQualify) {
        this.professQualify = professQualify == null ? null : professQualify.trim();
    }
}
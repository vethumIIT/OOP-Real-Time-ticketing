package com.project.realtime_ticketing_system.models.data_transfer_objects;

public class RepoResponse {

    private String responseString = "";
    private Long responseLong = 0L;

    public RepoResponse() {
    }

    public RepoResponse(String responseString) {
        this.responseString = responseString;
    }

    public RepoResponse(Long responseLong) {
        this.responseLong = responseLong;
    }

    public RepoResponse(String responseString, Long responseLong) {
        this.responseString = responseString;
        this.responseLong = responseLong;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public Long getResponseLong() {
        return responseLong;
    }

    public void setResponseInt(Long responseLong) {
        this.responseLong = responseLong;
    }
}

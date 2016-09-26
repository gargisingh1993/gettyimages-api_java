package com.gettyimages.search;

public enum EditorialSegment
{
    NONE("None"),
    ARCHIVAL("archival"),
    ENTERTAINMENT("entertainment"),
    NEWS("news"),
    PUBLICITY("publicity"),
    ROYALTY("royalty"),
    SPORT("sport");

    /**
     * Used for query parameter naming during the REST call.
     */
    private String val;

    EditorialSegment(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }
}

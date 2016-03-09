package gem.com.support_client.network.dto;

import gem.com.support_client.network.model.FeedbackDetail;

/**
 * Created by phuongtd on 09/03/2016.
 */

public class ListFeedBackDTO {
    FeedbackDetail[] content;
    int totalElements;
    int totalPages;
    boolean last;
    int size;
    int number;
    Sort[] sort;
    int numberOfElements;
    boolean first;

    public Sort[] getSort() {
        return sort;
    }

    public void setSort(Sort[] sort) {
        this.sort = sort;
    }

    private class Sort {
        String direction;
        String property;
        boolean ignoreCase;
        String nullHandling;
        boolean ascending;

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public boolean isIgnoreCase() {
            return ignoreCase;
        }

        public void setIgnoreCase(boolean ignoreCase) {
            this.ignoreCase = ignoreCase;
        }

        public String getNullHandling() {
            return nullHandling;
        }

        public void setNullHandling(String nullHandling) {
            this.nullHandling = nullHandling;
        }

        public boolean isAscending() {
            return ascending;
        }

        public void setAscending(boolean ascending) {
            this.ascending = ascending;
        }

        public Sort(String direction, String property, boolean ignoreCase, String nullHandling, boolean ascending) {

            this.direction = direction;
            this.property = property;
            this.ignoreCase = ignoreCase;
            this.nullHandling = nullHandling;
            this.ascending = ascending;
        }
    }

    public ListFeedBackDTO() {
    }

    public ListFeedBackDTO(FeedbackDetail[] content, int totalElements, int totalPages, boolean last, int size, int number, Sort[] sort, int numberOfElements, boolean first) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
        this.size = size;
        this.number = number;
        this.sort = sort;
        this.numberOfElements = numberOfElements;
        this.first = first;
    }

    public FeedbackDetail[] getContent() {
        return content;
    }

    public void setContent(FeedbackDetail[] content) {
        this.content = content;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }


}

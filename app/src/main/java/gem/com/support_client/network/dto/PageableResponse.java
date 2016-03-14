package gem.com.support_client.network.dto;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by quanda on 07/03/2016.
 */
public class PageableResponse<T> {
    ArrayList<T> content = new ArrayList<>();
    boolean last;
    int totalPages;
    int totalElements;
    Sort[] sort;
    boolean first;
    int size;
    int numberOfElements;
    int number;

    public PageableResponse(ArrayList<T> content, boolean last, int totalPages, int totalElements, Sort[] sort, boolean first, int size, int numberOfElements, int number) {
        this.content = content;
        this.last = last;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.sort = sort;
        this.first = first;
        this.size = size;
        this.numberOfElements = numberOfElements;
        this.number = number;
    }

    public PageableResponse() {

    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public Sort[] getSort() {
        return sort;
    }

    public void setSort(Sort[] sort) {
        this.sort = sort;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<T> getContent() {
        return content;
    }

    public void setContent(ArrayList<T> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PageableResponse{" +
                "content=" + content +
                ", last=" + last +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", sort=" + Arrays.toString(sort) +
                ", first=" + first +
                ", size=" + size +
                ", numberOfElements=" + numberOfElements +
                ", number=" + number +
                '}';
    }

    public static class Sort {
        String direction;
        String property;
        boolean ignoreCase;
        String nullHandling;
        boolean ascending;

        public Sort(String direction, String property, boolean ignoreCase, String nullHandling, boolean ascending) {
            this.direction = direction;
            this.property = property;
            this.ignoreCase = ignoreCase;
            this.nullHandling = nullHandling;
            this.ascending = ascending;
        }

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
    }
}

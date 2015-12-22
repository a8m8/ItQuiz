package net.itquiz.utils;

/**
 * @author Artur Meshcheriakov
 */
public class Pagination {

    private Integer count;
    private Integer offset;
    private Integer maximum;
    private Integer beginIndex;
    private Integer endIndex;
    private Integer currentIndex;

    public Pagination(int count, long entityScope, int currentPage) {
        this.count = count;
        this.currentIndex = currentPage;
        int temp = (int) entityScope / count;
        this.maximum = (entityScope % count == 0) ? temp : temp + 1;
        this.offset = (currentPage - 1) * count;
        this.beginIndex = Math.max(1, currentPage - 5);
        this.endIndex = Math.min(beginIndex + 10, maximum);
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public Integer getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(Integer beginIndex) {
        this.beginIndex = beginIndex;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    public Integer getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Integer currentIndex) {
        this.currentIndex = currentIndex;
    }
}

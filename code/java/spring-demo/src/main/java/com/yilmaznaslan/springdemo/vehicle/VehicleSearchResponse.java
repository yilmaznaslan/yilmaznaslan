package com.yilmaznaslan.springdemo.vehicle;

import java.util.List;

public class VehicleSearchResponse {

    private List<VehicleDto> vehicles;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int size;
    private boolean first;
    private boolean last;
    private long numberOfElements;

    public VehicleSearchResponse() {}

    public VehicleSearchResponse(List<VehicleDto> vehicles, long totalElements, int totalPages, 
                                int currentPage, int size, boolean first, boolean last, 
                                long numberOfElements) {
        this.vehicles = vehicles;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.size = size;
        this.first = first;
        this.last = last;
        this.numberOfElements = numberOfElements;
    }

    public List<VehicleDto> getVehicles() { return vehicles; }
    public void setVehicles(List<VehicleDto> vehicles) { this.vehicles = vehicles; }

    public long getTotalElements() { return totalElements; }
    public void setTotalElements(long totalElements) { this.totalElements = totalElements; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public boolean isFirst() { return first; }
    public void setFirst(boolean first) { this.first = first; }

    public boolean isLast() { return last; }
    public void setLast(boolean last) { this.last = last; }

    public long getNumberOfElements() { return numberOfElements; }
    public void setNumberOfElements(long numberOfElements) { this.numberOfElements = numberOfElements; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<VehicleDto> vehicles;
        private long totalElements;
        private int totalPages;
        private int currentPage;
        private int size;
        private boolean first;
        private boolean last;
        private long numberOfElements;

        public Builder vehicles(List<VehicleDto> vehicles) {
            this.vehicles = vehicles;
            return this;
        }

        public Builder totalElements(long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public Builder totalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public Builder currentPage(int currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder first(boolean first) {
            this.first = first;
            return this;
        }

        public Builder last(boolean last) {
            this.last = last;
            return this;
        }

        public Builder numberOfElements(long numberOfElements) {
            this.numberOfElements = numberOfElements;
            return this;
        }

        public VehicleSearchResponse build() {
            final VehicleSearchResponse response = new VehicleSearchResponse();
            response.setVehicles(vehicles);
            response.setTotalElements(totalElements);
            response.setTotalPages(totalPages);
            response.setCurrentPage(currentPage);
            response.setSize(size);
            response.setFirst(first);
            response.setLast(last);
            response.setNumberOfElements(numberOfElements);
            return response;
        }
    }
}

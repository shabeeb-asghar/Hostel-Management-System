package Users_Package;

import java.util.ArrayList;
import java.util.List;

import Hostel_Package.Hostel;

public class Feedback {
    private List<String> feedbackList;
    private List<Hostel> hostellist; //booked hostels


    public Feedback() {
        this.feedbackList = new ArrayList<>();
       // this.hostelList = new ArrayList<>();
    }

    public List<String> getFeedback() {
        return feedbackList;
    }

    public void removeFeedback(String feedback) {
        feedbackList.remove(feedback);
    }

    public void addFeedback(String feedback) {
        feedbackList.add(feedback);
    }
}

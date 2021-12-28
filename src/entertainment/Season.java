package entertainment;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private List<Double> ratings;

    private List<String> ratingUsers;

    private Double rating;

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
        this.ratingUsers = new ArrayList<>();
        this.rating = 0.0;
    }
    public List<String> getRatingUsers() {
        return ratingUsers;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    public void setRating(final Double rating) {
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    /**
     * Calculeaza rating-ul folosind media aritmetica a tuturor
     * notelor primite. In cazul in care nu exista nicio nota, rating-ul
     * va ramane egal cu zero
     *
     */
    public void calculateRating() {
        rating = 0.0;
        double count = ratings.size();
        if (Double.compare(count, 0.0) == 0) {
            return;
        }
        for (Double x : ratings) {
            rating += x;
        }
        rating /= count;
    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }
}


package fileio;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a movie, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class MovieInputData extends ShowInput {
    /**
     * Duration in minutes of a season
     */
    private final int duration;

    private final List<Double> ratings;

    private Double rating;

    private Integer favorite;

    private Integer views;



    public MovieInputData(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratings = new ArrayList<>();
        this.rating = 0.0;
        this.favorite = 0;
        this.views = 0;
    }

    public int getDuration() {
        return duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(final Double rating) {
        this.rating = rating;
    }

    public void setFavorite(final int favorite) {
        this.favorite = favorite;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setViews(final Integer views) {
        this.views = views;
    }

    public Integer getViews() {
        return views;
    }

    /**
     * Calculeaza numarul de aparitii al filmului curent in
     * lista e favorite a tuturor utilizatorilor
     *
     * @param users Lista de utilizatori din baza de date
     */
    public void calculateFavorite(final List<UserInputData> users) {
            favorite = 0;
            for (UserInputData user : users) {
                for (String fav : user.getFavoriteMovies()) {
                    if (fav.equals(this.getTitle())) {
                        ++favorite;
                    }
                }
            }
    }

    /**
     * Calculeaza numarul de vizionari ale filmului curent in istoricul
     * tururor utilizatorilor
     *
     * @param users lista de utilizatori din baza de date
     */
    public void calculateViews(final List<UserInputData> users) {
           views = 0;
           for (UserInputData user : users) {
               if (user.getHistory().containsKey(this.getTitle())) {
                   views += user.getHistory().get(this.getTitle());
               }
           }
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
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}

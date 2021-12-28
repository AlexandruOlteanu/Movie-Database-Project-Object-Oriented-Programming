package fileio;

import entertainment.Season;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class SerialInputData extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    private Integer favorite;

    private int duration;

    private Double rating;

    private Integer views;

    public SerialInputData(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.rating = 0.0;
        this.favorite = 0;
        this.duration = 0;
        this.views = 0;
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setRating(final Double rating) {
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setFavorite(final int favorite) {
        this.favorite = favorite;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setViews(final int views) {
        this.views = views;
    }

    public Integer getViews() {
        return views;
    }

    /**
     *
     *
     */
    public void calculateDuration() {
        duration = 0;
        for (Season season : seasons) {
            duration += season.getDuration();
        }
    }

    /**
     * Calculeaza numarul de vizionari ale serialului curent in istoricul
     * tururor utilizatorilor
     *
     * @param users Lista de utilizatori din baza de date
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
     * Calculeaza rating-ul pentru fiecare sezon in parte al
     * serialului curent si apoi se face media aritmetica a acestora
     *
     */
    public void calculateRating() {
        rating = 0.0;
        if (seasons.isEmpty()) {
            return;
        }
        for (Season season : seasons) {
            season.calculateRating();
            rating += season.getRating();
        }
        Double count = (double) seasons.size();
        rating /= count;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}

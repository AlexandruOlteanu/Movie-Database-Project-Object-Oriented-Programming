package actions.recommendation;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa construita pentru a reuni filmele cu serialele, in felul acesta
 * am usurat considerabil unele task-uri ce s-au rezolvat mult mai eficient considerand
 * o lista "comuna" ce contine atat filme cat si seriale
 */

public final class AllVideos {

    private String title;

    private Double rating;

    private Integer favorite;

    private List<String> genres = new ArrayList<String>();

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setRating(final Double rating) {
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setGenres(final List<String> genres) {
        this.genres = genres;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setFavorite(final Integer favorite) {
        this.favorite = favorite;
    }

    public Integer getFavorite() {
        return favorite;
    }

}

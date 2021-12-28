package fileio;

import actor.ActorsAwards;

import java.util.ArrayList;
import java.util.Map;

/**
 * Information about an actor, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class ActorInputData {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;

    private Double rating;

    private Double videosPlayed;

    private Integer numberAwards;


    public ActorInputData(final String name, final String careerDescription,
                          final ArrayList<String> filmography,
                          final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.rating = 0.0;
        this.videosPlayed = 0.0;
        this.numberAwards = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public void setRating(final Double rating) {
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setVideosPlayed(final Double videosPlayed) {
        this.videosPlayed = videosPlayed;
    }

    public Double getVideosPlayed() {
        return videosPlayed;
    }

    public Integer getNumberAwards() {
        return numberAwards;
    }

    /**
     *Parcurge map-ul ce retine toate premiile primite si le numara
     */
    public void calculateNumberAwards() {
        numberAwards = 0;
        for (Map.Entry<ActorsAwards, Integer> award : awards.entrySet()) {
            numberAwards += award.getValue();
        }
    }

    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}

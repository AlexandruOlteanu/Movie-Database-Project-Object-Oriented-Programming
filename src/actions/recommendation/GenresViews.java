package actions.recommendation;

import entertainment.Genre;

/**
 * Clasa creata pentru a calcula numarul de vizionari dintr-un anumit gen cinematografic.
 */

public final class GenresViews {

    private Genre name;

    private Integer views;

    public void setName(final Genre name) {
        this.name = name;
    }

    public Genre getName() {
        return name;
    }

    public void setViews(final Integer views) {
        this.views = views;
    }

    public Integer getViews() {
        return views;
    }
}

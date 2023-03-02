package nus.iss.tfip.pafworkshop26;

public interface Constants {
    public static final String DB_BOARDGAME = "bgg";
    public static final String COLLECTION_GAME = "game";
    public static final String COLLECTION_COMMENT = "comment";

    public static final String FIELD_OBJ_ID = "_id";
    public static final String FIELD_GID = "gid";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_YEAR = "year";
    public static final String FIELD_RANKING = "ranking";
    public static final String FIELD_RANK = "rank";
    public static final String FIELD_USERS_RATED = "users_rated";
    public static final String FIELD_URL = "url";
    public static final String FIELD_IMAGE = "image";
    public static final String FIELD_THUMBNAIL = "thumbnail";
    public static final String FIELD_TIMESTAMP = "timestamp";

    public static final String FIELD_C_ID = "c_id";
    public static final String FIELD_USER = "user";
    public static final String FIELD_RATING = "rating";
    public static final String FIELD_C_TEXT = "c_text";

    public static final String FIELD_AVERAGE = "average";
    public static final String FIELD_COMMENT = "comment";
    public static final String FIELD_REVIEW_ID = "review_id";
    public static final String FIELD_GAME_NAME = "game.name";
    public static final String FIELD_GAMES = "games";

    public static final String VALUE_OBJ_ID = "$_id";
    public static final String VALUE_C_ID = "$c_id";
    public static final String VALUE_GID = "$gid";
    public static final String VALUE_REVIEW_ID = "$review_id";
    public static final String VALUE_COMMENT_CID = "$comment.c_id";
    public static final String VALUE_COMMENT = "$comment";
    public static final String VALUE_DATENOW = "$$NOW";
    public static final String VALUE_RATING = "$rating";
    public static final String VALUE_USER = "$user";
    public static final String VALUE_C_TEXT = "$c_text";
    public static final String VALUE_GAME_NAME = "$game.name";
    public static final String VALUE_NAME = "$name";
}
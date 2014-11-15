package edu.asu.stratego.media;

import java.util.HashMap;

import javafx.scene.image.Image;

public class ImageConstants {
    
    public final static Image stratego_logo = new Image(ImageConstants.class.getResource("images/board/stratego_logo.png").toString());
    
    // Board Images.
    public final static Image SETUP_PANEL  = new Image(ImageConstants.class.getResource("images/board/setup_panel.png").toString());
    public final static Image BORDER       = new Image(ImageConstants.class.getResource("images/board/border.png").toString());
    public final static Image DARK_GRASS   = new Image(ImageConstants.class.getResource("images/board/grass1.png").toString());
    public final static Image LIGHT_GRASS  = new Image(ImageConstants.class.getResource("images/board/grass2.png").toString());

    public final static Image HIGHLIGHT_NONE    = new Image(ImageConstants.class.getResource("images/board/highlight_none.png").toString());
    public final static Image HIGHLIGHT_VALID   = new Image(ImageConstants.class.getResource("images/board/highlight_valid.png").toString());
    public final static Image HIGHLIGHT_INVALID = new Image(ImageConstants.class.getResource("images/board/highlight_invalid.png").toString());
    
    public final static Image LAKE_1_1 = new Image(ImageConstants.class.getResource("images/board/lake1_1.png").toString());
    public final static Image LAKE_1_2 = new Image(ImageConstants.class.getResource("images/board/lake1_2.png").toString());
    public final static Image LAKE_1_3 = new Image(ImageConstants.class.getResource("images/board/lake1_3.png").toString());
    public final static Image LAKE_1_4 = new Image(ImageConstants.class.getResource("images/board/lake1_4.png").toString());
    
    public final static Image LAKE_2_1 = new Image(ImageConstants.class.getResource("images/board/lake2_1.png").toString());
    public final static Image LAKE_2_2 = new Image(ImageConstants.class.getResource("images/board/lake2_2.png").toString());
    public final static Image LAKE_2_3 = new Image(ImageConstants.class.getResource("images/board/lake2_3.png").toString());
    public final static Image LAKE_2_4 = new Image(ImageConstants.class.getResource("images/board/lake2_4.png").toString());

    // Piece Images.
    public final static Image RED_02   = new Image(ImageConstants.class.getResource("images/pieces/red/red_02.png").toString());
    public final static Image RED_03   = new Image(ImageConstants.class.getResource("images/pieces/red/red_03.png").toString());
    public final static Image RED_04   = new Image(ImageConstants.class.getResource("images/pieces/red/red_04.png").toString());
    public final static Image RED_05   = new Image(ImageConstants.class.getResource("images/pieces/red/red_05.png").toString());
    public final static Image RED_06   = new Image(ImageConstants.class.getResource("images/pieces/red/red_06.png").toString());
    public final static Image RED_07   = new Image(ImageConstants.class.getResource("images/pieces/red/red_07.png").toString());
    public final static Image RED_08   = new Image(ImageConstants.class.getResource("images/pieces/red/red_08.png").toString());
    public final static Image RED_09   = new Image(ImageConstants.class.getResource("images/pieces/red/red_09.png").toString());
    public final static Image RED_10   = new Image(ImageConstants.class.getResource("images/pieces/red/red_10.png").toString());
    public final static Image RED_SPY  = new Image(ImageConstants.class.getResource("images/pieces/red/red_spy.png").toString());
    public final static Image RED_BACK = new Image(ImageConstants.class.getResource("images/pieces/red/red_back.png").toString());
    public final static Image RED_BOMB = new Image(ImageConstants.class.getResource("images/pieces/red/red_bomb.png").toString());
    public final static Image RED_FLAG = new Image(ImageConstants.class.getResource("images/pieces/red/red_flag.png").toString());
    
    public final static Image BLUE_02   = new Image(ImageConstants.class.getResource("images/pieces/blue/blue_02.png").toString());
    public final static Image BLUE_03   = new Image(ImageConstants.class.getResource("images/pieces/blue/blue_03.png").toString());
    public final static Image BLUE_04   = new Image(ImageConstants.class.getResource("images/pieces/blue/blue_04.png").toString());
    public final static Image BLUE_05   = new Image(ImageConstants.class.getResource("images/pieces/blue/blue_05.png").toString());
    public final static Image BLUE_06   = new Image(ImageConstants.class.getResource("images/pieces/blue/blue_06.png").toString());
    public final static Image BLUE_07   = new Image(ImageConstants.class.getResource("images/pieces/blue/blue_07.png").toString());
    public final static Image BLUE_08   = new Image(ImageConstants.class.getResource("images/pieces/blue/blue_08.png").toString());
    public final static Image BLUE_09   = new Image(ImageConstants.class.getResource("images/pieces/blue/blue_09.png").toString());
    public final static Image BLUE_10   = new Image(ImageConstants.class.getResource("images/pieces/blue/blue_10.png").toString());
    public final static Image BLUE_SPY  = new Image(ImageConstants.class.getResource("images/pieces/blue/blue_spy.png").toString());
    public final static Image BLUE_BACK = new Image(ImageConstants.class.getResource("images/pieces/blue/blue_back.png").toString());
    public final static Image BLUE_BOMB = new Image(ImageConstants.class.getResource("images/pieces/blue/blue_bomb.png").toString());
    public final static Image BLUE_FLAG = new Image(ImageConstants.class.getResource("images/pieces/blue/blue_flag.png").toString());
    
    // Piece Image Map (String -> Image).
    public final static HashMap<String, Image> PIECE_MAP = new HashMap<String, Image>(24);
    static {
        // Red Pieces.
        PIECE_MAP.put("RED_02", RED_02);
        PIECE_MAP.put("RED_03", RED_03);
        PIECE_MAP.put("RED_04", RED_04);
        PIECE_MAP.put("RED_05", RED_05);
        PIECE_MAP.put("RED_06", RED_06);
        PIECE_MAP.put("RED_07", RED_07);
        PIECE_MAP.put("RED_08", RED_08);
        PIECE_MAP.put("RED_09", RED_09);
        PIECE_MAP.put("RED_10", RED_10);
        PIECE_MAP.put("RED_SPY", RED_SPY);
        PIECE_MAP.put("RED_BOMB", RED_BOMB);
        PIECE_MAP.put("RED_FLAG", RED_FLAG);
        PIECE_MAP.put("RED_BACK", RED_BACK);
        
        // Blue Pieces.
        PIECE_MAP.put("BLUE_02", BLUE_02);
        PIECE_MAP.put("BLUE_03", BLUE_03);
        PIECE_MAP.put("BLUE_04", BLUE_04);
        PIECE_MAP.put("BLUE_05", BLUE_05);
        PIECE_MAP.put("BLUE_06", BLUE_06);
        PIECE_MAP.put("BLUE_07", BLUE_07);
        PIECE_MAP.put("BLUE_08", BLUE_08);
        PIECE_MAP.put("BLUE_09", BLUE_09);
        PIECE_MAP.put("BLUE_10", BLUE_10);
        PIECE_MAP.put("BLUE_SPY", BLUE_SPY);
        PIECE_MAP.put("BLUE_BOMB", BLUE_BOMB);
        PIECE_MAP.put("BLUE_FLAG", BLUE_FLAG);
        PIECE_MAP.put("BLUE_BACK", BLUE_BACK);
    }
}
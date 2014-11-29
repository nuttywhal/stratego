package edu.asu.stratego.util;

import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import edu.asu.stratego.media.ImageConstants;
import edu.asu.stratego.media.SoundConstants;

public class HashTables{
    // Piece Image Map (String -> Image).
    public final static HashMap<String, Image> PIECE_MAP = new HashMap<String, Image>(24);
    static {
        // RED Pieces.
        PIECE_MAP.put("RED_02", ImageConstants.RED_02);
        PIECE_MAP.put("RED_03", ImageConstants.RED_03);
        PIECE_MAP.put("RED_04", ImageConstants.RED_04);
        PIECE_MAP.put("RED_05", ImageConstants.RED_05);
        PIECE_MAP.put("RED_06", ImageConstants.RED_06);
        PIECE_MAP.put("RED_07", ImageConstants.RED_07);
        PIECE_MAP.put("RED_08", ImageConstants.RED_08);
        PIECE_MAP.put("RED_09", ImageConstants.RED_09);
        PIECE_MAP.put("RED_10", ImageConstants.RED_10);
        PIECE_MAP.put("RED_SPY", ImageConstants.RED_SPY);
        PIECE_MAP.put("RED_BOMB", ImageConstants.RED_BOMB);
        PIECE_MAP.put("RED_FLAG", ImageConstants.RED_FLAG);
        PIECE_MAP.put("RED_BACK", ImageConstants.RED_BACK);
        
        // Blue Pieces.
        PIECE_MAP.put("BLUE_02", ImageConstants.BLUE_02);
        PIECE_MAP.put("BLUE_03", ImageConstants.BLUE_03);
        PIECE_MAP.put("BLUE_04", ImageConstants.BLUE_04);
        PIECE_MAP.put("BLUE_05", ImageConstants.BLUE_05);
        PIECE_MAP.put("BLUE_06", ImageConstants.BLUE_06);
        PIECE_MAP.put("BLUE_07", ImageConstants.BLUE_07);
        PIECE_MAP.put("BLUE_08", ImageConstants.BLUE_08);
        PIECE_MAP.put("BLUE_09", ImageConstants.BLUE_09);
        PIECE_MAP.put("BLUE_10", ImageConstants.BLUE_10);
        PIECE_MAP.put("BLUE_SPY", ImageConstants.BLUE_SPY);
        PIECE_MAP.put("BLUE_BOMB", ImageConstants.BLUE_BOMB);
        PIECE_MAP.put("BLUE_FLAG", ImageConstants.BLUE_FLAG);
        PIECE_MAP.put("BLUE_BACK", ImageConstants.BLUE_BACK);
    }

    public final static HashMap<String, AudioClip> SOUND_MAP = new HashMap<String, AudioClip>(1);
    static {
    	// TODO Sound hashmap disabled
        //SOUND_MAP.put("cornfield", SoundConstants.CORNFIELD);
    }
}
package games.negative.bingo.state;

import lombok.Data;

/**
 * Class representation of state.json
 *
 * state.json holds persistent data about the current state of
 * the game and plugin at runtime
 */
@Data
public class GameState {

    // Is the plugin "disabled"?
    // This is a kind of "soft disable" mode
    // that allows the plugin to be enabled without messing
    // with other stuff
    private boolean enabled = false;
}

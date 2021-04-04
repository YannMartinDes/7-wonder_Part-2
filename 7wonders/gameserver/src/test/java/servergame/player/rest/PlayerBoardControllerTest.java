package servergame.player.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import commun.communication.CommunicationMessages;
import commun.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    PlayerBoardController playerBoardController;

    @BeforeEach
    void initTest() {
        // on injecte le spy
        List<Player> playerList = new ArrayList<Player>();
        playerList.add(new Player("test1"));
        playerList.add(new Player("test2"));
        playerList.add(new Player("test3"));
        playerList.add(new Player("test4"));
        playerList.add(new Player("test5"));

        playerBoardController.setPlayers(playerList);
    }

    @Test
    public void loadBoardTest() throws Exception {

        for(int i =0; i <5; i++){
            Player p = playerBoardController.getPlayers().get(i);

            this.mockMvc.perform(get("/"+CommunicationMessages.BOARD + "/"+i))
                    .andExpect(status().isAccepted())
                    .andExpect(content().string(objectMapper.writeValueAsString(p)));
        }
    }

    @Test
    public void loadBoardTestNull() throws Exception {

        playerBoardController.setPlayers(null);

        this.mockMvc.perform(get("/"+CommunicationMessages.BOARD + "/0"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void loadBoardTestOutOfBound() throws Exception {

        this.mockMvc.perform(get("/"+CommunicationMessages.BOARD + "/-1"))
                .andExpect(status().isNotAcceptable());

        //We have only 5 player here.
        this.mockMvc.perform(get("/"+CommunicationMessages.BOARD + "/6"))
                .andExpect(status().isNotAcceptable());
    }
}

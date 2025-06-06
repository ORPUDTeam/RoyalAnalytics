package org.example.royaleanalytics.controller;


import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.response.PlayerDto;
import org.example.royaleanalytics.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayersController {

    private final UserService userService;

    @GetMapping("/{player_tag}")
    public PlayerDto getPlayer(@PathVariable String player_tag){
        return userService.getPlayer(player_tag);
    }

}

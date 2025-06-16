package jp.furykasukabe.shogi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "templates", schema = "public")
public class Template {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    @Column(name = "player_id") 
    private int playerId; 
    
    @NotBlank
    private String name;
    
    @Column(name = "play_first")
    @NotNull
    private boolean playFirst; 
    
    @NotNull
    private boolean available;
    
    @Size(max = 255)
    private String context;
}

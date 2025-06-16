package jp.furykasukabe.shogi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "lists", schema = "public")
@Data
public class ShogiList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    @Column(name = "template_id") 
    private int templateId; 
    
    private int num; 
    
    @Column(name = "before_id") 
    private int beforeId;
    
    @Column(name = "before_x") 
    private int beforeX; 
    
    @Column(name = "before_y") 
    private int beforeY;
    
    @Column(name = "target_x") 
    private int targetX; 
    
    @Column(name = "target_y") 
    private int targetY;
    
    @Column(name = "is_promoted") 
    private boolean isPromoted;
}

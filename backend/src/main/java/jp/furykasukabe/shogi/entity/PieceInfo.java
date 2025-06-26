package jp.furykasukabe.shogi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pieces", schema = "public")
@Data
@NoArgsConstructor
public class PieceInfo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	private String name;
	
	@Column(name = "promotedName")
	private String promotedName;
	
}

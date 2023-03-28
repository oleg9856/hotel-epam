package org.hotel.entity.room;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomSearch {
	private String orderBy;
	private int currentPage;
	private int itemsPerPage;
	private int pageCount;
}
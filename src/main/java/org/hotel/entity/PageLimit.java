package org.hotel.entity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageLimit {
    private int currentPage;
    private int itemsPerPage;
    private int pageCount;
}

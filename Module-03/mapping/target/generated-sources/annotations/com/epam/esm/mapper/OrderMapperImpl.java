package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderInfoDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-16T01:11:31+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderInfoDto mapToOrderInfo(Order source) {
        if ( source == null ) {
            return null;
        }

        OrderInfoDto.OrderInfoDtoBuilder orderInfoDto = OrderInfoDto.builder();

        if ( source.getId() != null ) {
            orderInfoDto.orderId( source.getId() );
        }
        if ( source.getCost() != null ) {
            orderInfoDto.cost( source.getCost() );
        }
        if ( source.getCreatedAt() != null ) {
            orderInfoDto.createdAt( source.getCreatedAt() );
        }
        if ( source.getGiftCertificate() != null ) {
            orderInfoDto.giftCertificate( giftCertificateToGiftCertificateDto( source.getGiftCertificate() ) );
        }

        return orderInfoDto.build();
    }

    @Override
    public OrderDto map(Order source) {
        if ( source == null ) {
            return null;
        }

        OrderDto.OrderDtoBuilder orderDto = OrderDto.builder();

        if ( source.getId() != null ) {
            orderDto.orderId( source.getId() );
        }
        Long id = sourceGiftCertificateId( source );
        if ( id != null ) {
            orderDto.giftCertificateId( id );
        }

        return orderDto.build();
    }

    protected TagDto tagToTagDto(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        TagDto.TagDtoBuilder tagDto = TagDto.builder();

        if ( tag.getId() != null ) {
            tagDto.id( tag.getId() );
        }
        if ( tag.getName() != null ) {
            tagDto.name( tag.getName() );
        }

        return tagDto.build();
    }

    protected List<TagDto> tagListToTagDtoList(List<Tag> list) {
        if ( list == null ) {
            return null;
        }

        List<TagDto> list1 = new ArrayList<TagDto>( list.size() );
        for ( Tag tag : list ) {
            list1.add( tagToTagDto( tag ) );
        }

        return list1;
    }

    protected GiftCertificateDto giftCertificateToGiftCertificateDto(GiftCertificate giftCertificate) {
        if ( giftCertificate == null ) {
            return null;
        }

        GiftCertificateDto.GiftCertificateDtoBuilder giftCertificateDto = GiftCertificateDto.builder();

        if ( giftCertificate.getId() != null ) {
            giftCertificateDto.id( giftCertificate.getId() );
        }
        if ( giftCertificate.getName() != null ) {
            giftCertificateDto.name( giftCertificate.getName() );
        }
        if ( giftCertificate.getDescription() != null ) {
            giftCertificateDto.description( giftCertificate.getDescription() );
        }
        if ( giftCertificate.getPrice() != null ) {
            giftCertificateDto.price( giftCertificate.getPrice() );
        }
        if ( giftCertificate.getDuration() != null ) {
            giftCertificateDto.duration( giftCertificate.getDuration() );
        }
        if ( giftCertificate.getCreateDate() != null ) {
            giftCertificateDto.createDate( giftCertificate.getCreateDate() );
        }
        if ( giftCertificate.getLastUpdateDate() != null ) {
            giftCertificateDto.lastUpdateDate( giftCertificate.getLastUpdateDate() );
        }
        List<TagDto> list = tagListToTagDtoList( giftCertificate.getTags() );
        if ( list != null ) {
            giftCertificateDto.tags( list );
        }

        return giftCertificateDto.build();
    }

    private Long sourceGiftCertificateId(Order order) {
        if ( order == null ) {
            return null;
        }
        GiftCertificate giftCertificate = order.getGiftCertificate();
        if ( giftCertificate == null ) {
            return null;
        }
        Long id = giftCertificate.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

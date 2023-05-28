package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-16T01:11:31+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public TagDto map(Tag tag) {
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

    @Override
    public Tag map(TagDto tag) {
        if ( tag == null ) {
            return null;
        }

        Tag.TagBuilder tag1 = Tag.builder();

        if ( tag.getId() != null ) {
            tag1.id( tag.getId() );
        }
        if ( tag.getName() != null ) {
            tag1.name( tag.getName() );
        }

        return tag1.build();
    }
}

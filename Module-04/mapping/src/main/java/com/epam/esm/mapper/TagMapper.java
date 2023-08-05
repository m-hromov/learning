package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TagMapper {
    TagDto map(Tag tag);

    Tag map(TagDto tag);
}

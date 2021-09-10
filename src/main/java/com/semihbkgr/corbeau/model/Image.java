package com.semihbkgr.corbeau.model;

import com.semihbkgr.corbeau.model.base.AllAuditable;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Table("images")
public class Image extends AllAuditable implements Serializable {

    public static final Image EMPTY_IMAGE = new ImmutableImage();

    @Id
    private int id;

    @NotEmpty(message = "{}")
    @Length(min = 4, max = 32, message = "{}")
    @Pattern(regexp = "", message = "{}")
    private String name;

    @Length(min = 2, max = 4, message = "{}")
    @Pattern(regexp = "", message = "{}")
    private String extension;

    @Max(value = 3_000, message = "{}")
    @Min(value = 300, message = "{}")
    private int width;

    @Max(value = 3_000, message = "{}")
    @Min(value = 300, message = "{}")
    private int height;

    private long size;

    private static class ImmutableImage extends Image {

        private ImmutableImage() {
            super(0, null, null, 0, 0, 0);
            super.createdBy = null;
            super.updatedBy = null;
            super.createdAt = 0L;
            super.updatedAt = 0L;
        }

        @Override
        public void setId(int id) {
            throw new UnsupportedOperationException("ImmutableImage does not support any modification opreation");
        }

        @Override
        public void setName(@NotEmpty(message = "{}") @Length(min = 4, max = 32, message = "{}") @Pattern(regexp = "", message = "{}") String name) {
            throw new UnsupportedOperationException("ImmutableImage does not support any modification opreation");
        }

        @Override
        public void setExtension(@Length(min = 2, max = 4, message = "{}") @Pattern(regexp = "", message = "{}") String extension) {
            throw new UnsupportedOperationException("ImmutableImage does not support any modification opreation");
        }

        @Override
        public void setWidth(@Max(value = 3_000, message = "{}") @Min(value = 300, message = "{}") int width) {
            throw new UnsupportedOperationException("ImmutableImage does not support any modification opreation");
        }

        @Override
        public void setHeight(@Max(value = 3_000, message = "{}") @Min(value = 300, message = "{}") int height) {
            throw new UnsupportedOperationException("ImmutableImage does not support any modification opreation");
        }

        @Override
        public void setSize(long size) {
            throw new UnsupportedOperationException("ImmutableImage does not support any modification opreation");
        }

        @Override
        public void setCreatedBy(String createdBy) {
            throw new UnsupportedOperationException("ImmutableImage does not support any modification opreation");
        }

        @Override
        public void setUpdatedBy(String updatedBy) {
            throw new UnsupportedOperationException("ImmutableImage does not support any modification opreation");
        }

        @Override
        public void setCreatedAt(long createdAt) {
            throw new UnsupportedOperationException("ImmutableImage does not support any modification opreation");
        }

        @Override
        public void setUpdatedAt(long updatedAt) {
            throw new UnsupportedOperationException("ImmutableImage does not support any modification opreation");
        }

    }

}

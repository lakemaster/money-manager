package com.jojobi.mm.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.lang.reflect.Field;
import java.util.List;

@Slf4j
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public <T extends BaseEntity> T merge(T entity) {
        // todo: maybe use BeanUtils copyProperties from apache.commons, but what about merge of Lists
        copyFields(entity, this);
        return (T) this;
    }

    private <T extends BaseEntity, Y extends BaseEntity> void copyFields(T from, Y too) {

        Class<? extends Object> fromClass = from.getClass();
        Field[] fromFields = fromClass.getDeclaredFields();

        Class<? extends Object> tooClass = too.getClass();
        Field[] tooFields = tooClass.getDeclaredFields();

        if (fromFields != null && tooFields != null) {
            for (Field tooF : tooFields) {
                log.debug("copy field {}.{} of type {}", tooClass.getSimpleName(), tooF.getName(), tooF.getType().getSimpleName());
                try {
                    // Check if that fields exists in the other method
                    Field fromF = fromClass.getDeclaredField(tooF.getName());
                    if (fromF.getType().equals(tooF.getType())) {
                        fromF.setAccessible(true);
                        Object value = fromF.get(from);
                        // todo: clone not immutable objects
                        if (value != null) {
                            tooF.setAccessible(true);
                            if (value instanceof List) {
                                List list = (List) value;
                                if (list.size() > 0) {
                                    ((List) tooF.get(too)).clear();
                                    ((List) tooF.get(too)).add(list);
                                }
                            } else {
                                tooF.set(too, value);
                            }
                        }
                    }
                } catch (SecurityException|NoSuchFieldException|IllegalArgumentException|IllegalAccessException e) {
                    log.error("Error copying field {}.{} during merge operation",
                            tooClass.getSimpleName(), tooF.getName(), e);
                }
            }
        }
    }
}

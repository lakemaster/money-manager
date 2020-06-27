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

    private <T extends BaseEntity, Y extends BaseEntity> void copyFields(T fromObj, Y toObj) {
        Class<? extends Object> fromClass = fromObj.getClass();
        Field[] fromFields = fromClass.getDeclaredFields();

        Class<? extends Object> toClass = toObj.getClass();
        Field[] toFields = toClass.getDeclaredFields();

        if (fromFields != null && toFields != null) {
            for (Field toField : toFields) {
                log.debug("copy field {}.{} of type {}", toClass.getSimpleName(), toField.getName(), toField.getType().getSimpleName());
                try {
                    // Check if that fields exists in the other method
                    Field fromField = fromClass.getDeclaredField(toField.getName());
                    if (fromField.getType().equals(toField.getType())) {
                        fromField.setAccessible(true);
                        Object value = fromField.get(fromObj);
                        // todo: clone not immutable objects
                        if (value != null) {
                            toField.setAccessible(true);
                            if (value instanceof List) {
                                List list = (List) value;
                                if (list.size() > 0) {
                                    ((List) toField.get(toObj)).clear();
                                    ((List) toField.get(toObj)).add(list);
                                }
                            } else {
                                toField.set(toObj, value);
                            }
                        }
                    }
                } catch (SecurityException|NoSuchFieldException|IllegalArgumentException|IllegalAccessException e) {
                    log.error("Error copying field {}.{} during merge operation",
                            toClass.getSimpleName(), toField.getName(), e);
                }
            }
        }
    }
}

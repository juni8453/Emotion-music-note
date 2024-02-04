package com.emotionmusicnote.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @MappedSuperclass : 다른 Entity 들이 BaseTimeEntity 를 상속할 경우 BaseTimeEntity 의 Filed 를 Column 으로 인식
 * @EntityListeners :
 * "Specifies the callback listener classes to be used for an entity or mapped superclass.
 * This annotation may be applied to an entity class for mapped superclass."
 * (Entity 또는 매핑된 Super class 에 적용, 콜백될 클래스를 지정하는 어노테이션)
 * 즉, AuditingEntityListener 클래스가 callback listener 로 지정되어 Entity 에서 이벤트가 발생할 때마다 특정 로직을 수행
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {

  @CreatedDate
  private LocalDate createdDate;

  @LastModifiedDate
  private LocalDate modifiedDate;

}

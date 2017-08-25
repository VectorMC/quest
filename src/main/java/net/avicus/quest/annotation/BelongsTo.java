package net.avicus.quest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.avicus.quest.model.Model;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BelongsTo {

  Class<? extends Model> model();

  String foreignKey();
}

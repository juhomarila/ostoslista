package fi.ruoka.ostoslista.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(indexName = "tuote_index")
public class TuoteDocument {
    @Id
    private Long id;
    
    @Field(type = FieldType.Text, analyzer = "standard")
    private String tuote;

    @Field(type = FieldType.Integer)
    private int selectionCount;
}

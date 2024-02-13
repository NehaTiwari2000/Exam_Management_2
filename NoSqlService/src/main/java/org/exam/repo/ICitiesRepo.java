package org.exam.repo;

import org.exam.document.Cities;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ICitiesRepo extends MongoRepository<Cities,String> {
//    @Query("""
//             db.cities.aggregate([{$match:{country:"India"}},{$sort:{population:-1}},{$match:{continent:"Asia"}},{$project:{"population":"$population","name":"$country"}}])
//            """)

    @Query(value = "{}")
    List<Cities> manipulatedData();


}

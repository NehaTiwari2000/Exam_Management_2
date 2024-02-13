package org.exam.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exam.document.Cities;
import org.exam.document.DatabaseSequence;
import org.exam.document.User;
import org.exam.exception.ResourceNotFoundException;
import org.exam.repo.ICitiesRepo;
import org.exam.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ICitiesRepo citiesRepo;

    @GetMapping("/get-all-user")
    public ResponseEntity<?> getAllUser() {
        List<User> all = mongoOperations.findAll(User.class);
        List<User> users = mongoTemplate.findAll(User.class);
        return ResponseEntity.ok(this.userRepo.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(User user) {
        log.info("User : {}", user);
        log.info("User name : {}", user.getName());
        this.userRepo.save(new User(generateSequence(User.SEQUENCE_NAME), user.getName()));
        return ResponseEntity.ok("User saved......");
    }

    public Integer generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

    @GetMapping("/user-by-id")
    public ResponseEntity<?> getUserById(@RequestParam Integer userId) {
        return ResponseEntity.ok(this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No Data Found")));
    }

    @PutMapping("/update-user-by-id")
    public ResponseEntity<?> updateUserById(@RequestBody User user, @RequestParam Integer id) {
        Optional<User> userOptional = this.userRepo.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setName(user.getName());
            this.userRepo.save(existingUser);
            return ResponseEntity.ok("Data updated....");
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/get-all-cities")
    public ResponseEntity<?> getAllCities() {
        Query query = new Query();
        query.addCriteria(Criteria.where("country").is("India"));
        String queryString = String.format("{\"country\": \"%s\"}", "India");
        BasicQuery basicQuery = new BasicQuery(queryString);
        basicQuery.fields().exclude("_id").exclude("name","country");
//        return ResponseEntity.ok(this.citiesRepo.findAll());

        return ResponseEntity.ok(mongoTemplate.find(basicQuery, Cities.class,"cities"));
    }

    @GetMapping("/get-all-cities-on-a-filter")
    public ResponseEntity<?> getAllCitiesOnAFilter(){
        Comparator<Cities> comparable = new Comparator<Cities>() {

            @Override
            public int compare(Cities o1, Cities o2) {
                return o1.getPopulation().intValue()-o2.getPopulation().intValue();
            }
        };


        List<OutputCity> list = this.citiesRepo.findAll().stream().
                filter(cities -> cities.getCountry().equals("India"))
                .sorted(comparable)
                .filter(cities -> cities.getContinent()
                        .equals("Asia")).map(cities -> {
                    OutputCity outputCity = new OutputCity();
                    outputCity.setName(cities.getName());
                    outputCity.setPopulation(cities.getPopulation());
                    return outputCity;
                }).toList();

        return ResponseEntity.ok(list);
    }


    @GetMapping("/get-cities-by-name")
    public ResponseEntity<?> getCitiesByName(){
        return ResponseEntity.ok(this.citiesRepo.manipulatedData());
    }
}
@Data
@NoArgsConstructor
@AllArgsConstructor
class OutputCity{
    private Double population;
    private String name;
}
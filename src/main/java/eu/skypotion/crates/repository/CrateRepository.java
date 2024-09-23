package eu.skypotion.crates.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import eu.skypotion.crates.model.Crate;

@Collection("crates")
public interface CrateRepository extends Repository<Crate, String> {
}

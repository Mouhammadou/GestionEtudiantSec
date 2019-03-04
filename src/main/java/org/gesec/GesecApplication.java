package org.gesec;

import org.gesec.dao.EtudiantRepository;
import org.gesec.entities.Etudiant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@SpringBootApplication
public class GesecApplication {

	public static void main(String[] args) throws ParseException {

		ApplicationContext ctx =  SpringApplication.run(GesecApplication.class, args);
		/*EtudiantRepository etudiantRepository = ctx.getBean(EtudiantRepository.class);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		etudiantRepository.save(new Etudiant("BA", "Doudou", df.parse("1996-01-01")));
		etudiantRepository.save(new Etudiant("NDOYE", "Arame", df.parse("1995-12-14")));
		etudiantRepository.save(new Etudiant("FALL", "Gora", df.parse("1995-03-19")));

		List<Etudiant> etds = etudiantRepository.findAll();

		etds.forEach(etudiant -> System.out.println(etudiant.getNom()));*/
	}

}

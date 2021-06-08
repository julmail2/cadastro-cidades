package com.tecsidel.cadastrocidade.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tecsidel.cadastrocidade.controller.form.CidadeForm;
import com.tecsidel.cadastrocidade.model.Cidade;
import com.tecsidel.cadastrocidade.model.UF;
import com.tecsidel.cadastrocidade.repository.CidadeRepository;
import com.tecsidel.cadastrocidade.repository.UFRepository;

@Controller
@RequestMapping("/cadastro")
public class CadastroController {
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private UFRepository ufRepository;

	@GetMapping
	public String home(Model model) {
		return "redirect:/cadastro/lista-cidades";
	}

	@GetMapping("formulario")
	public String formularioIncluirCidade(Model model, CidadeForm cidadeForm) {
		List<UF> listaUf = ufRepository.findByOrderBySiglaAsc();
		model.addAttribute("listaUf", listaUf);
		return "cadastro/formulario";
	}

	@GetMapping("busca")
	public String formularioBusca(CidadeForm cidadeForm, Model model) {
		List<UF> listaUf = ufRepository.findByOrderBySiglaAsc();
		model.addAttribute("listaUf", listaUf);
		return "cadastro/busca";
	}

	@PostMapping("salvar")
	public String salvar(@Valid CidadeForm cidadeForm, BindingResult result, Model model) {

		if (result.hasErrors()) {
			List<UF> listaUf = ufRepository.findByOrderBySiglaAsc();
			model.addAttribute("listaUf", listaUf);
			return "cadastro/formulario";
		}

		Cidade cidade = cidadeForm.toCidade();
		cidadeRepository.save(cidade);
		return "redirect:/cadastro/lista-cidades";
	}

	@GetMapping("lista-cidades")
	public String listaCidades(Model model) {
		List<Cidade> listaCidades = new ArrayList<Cidade>();
		listaCidades = cidadeRepository.findAll();
		List<Cidade> cidades = listaCidades.stream().sorted((c1, c2) -> {
			if (c1.getUf().getSigla().equals(c2.getUf().getSigla())) {
				return c1.getNome().compareTo(c2.getNome());
			} else {
				return c1.getUf().getSigla().compareTo(c2.getUf().getSigla());
			}

		}).collect(Collectors.toList());

		model.addAttribute("cidades", cidades);
		return "cadastro/lista-cidades";
	}

	@GetMapping("busca-cidades")
	public String buscaCidades(Model model, @RequestParam("nome") String nome, @RequestParam("ufId") String ufId) {
		List<Cidade> listaCidades = new ArrayList<Cidade>();

		if (ufId.isBlank() && !nome.isBlank()) {
			listaCidades = cidadeRepository.findByNomeContainingIgnoreCase(nome);
		} else if (!ufId.isBlank() && nome.isBlank()) {
			UF uf = new UF();
			uf.setId(Integer.parseInt(ufId));
			listaCidades = cidadeRepository.findByUf(uf);
		} else if (!ufId.isBlank() && !nome.isBlank()) {
			UF uf = new UF();
			uf.setId(Integer.parseInt(ufId));
			listaCidades = cidadeRepository.findByUfAndNomeContainingIgnoreCase(uf, nome);
		} else {
			listaCidades = cidadeRepository.findAll();
		}

		List<Cidade> cidades = listaCidades.stream().sorted((c1, c2) -> {
			if (c1.getUf().getSigla().equals(c2.getUf().getSigla())) {
				return c1.getNome().compareTo(c2.getNome());
			} else {
				return c1.getUf().getSigla().compareTo(c2.getUf().getSigla());
			}

		}).collect(Collectors.toList());

		model.addAttribute("cidades", cidades);
		return "cadastro/lista-cidades";
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Long> removeCidade(@PathVariable Long id) {
		cidadeRepository.deleteById(id);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public String formularioEditaCidade(@PathVariable Long id, Model model, CidadeForm cidadeForm) {
		Optional<Cidade> cidade = cidadeRepository.findById(id);
		if (cidade.isPresent()) {
			cidadeForm.setUfId(cidade.get().getUf().getId());
			cidadeForm.setNome(cidade.get().getNome());
			cidadeForm.setId(cidade.get().getId());
		}
		List<UF> listaUf = ufRepository.findByOrderBySiglaAsc();
		model.addAttribute("listaUf", listaUf);
		return "cadastro/formulario";
	}

}

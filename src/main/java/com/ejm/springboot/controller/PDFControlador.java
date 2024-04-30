package com.ejm.springboot.controller;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.ItemSubproyecto;
import com.ejm.springboot.entity.Proyecto;
import com.ejm.springboot.entity.Subproyecto;
import com.ejm.springboot.repository.APUItemSubproyectoRepository;
import com.ejm.springboot.repository.ItemSubproyectoRepository;
import com.ejm.springboot.repository.SubproyectoRepository;
import com.ejm.springboot.service.APUItemSubproyectoService;
import com.ejm.springboot.service.ItemSubproyectoService;
import com.ejm.springboot.service.ProyectoService;
import com.ejm.springboot.service.SubproyectoService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.service.spi.ServiceBinding.ServiceLifecycleOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.io.IOException;
import java.util.List;

@Controller
public class PDFControlador {

	@Autowired
	ProyectoService proyectoService;
	@Autowired
	private SubproyectoRepository subproyectoRepository;

	@Autowired
	private ItemSubproyectoRepository itemSubproyectoRepository;

	@Autowired
	private APUItemSubproyectoRepository apuItemSubproyectoRepository;

    @GetMapping("/Proyectos/{id}/Visualizar/Generar_PDF")
    public void generatePdf(@PathVariable long id, HttpServletResponse response) throws DocumentException, IOException {

    	Proyecto proyecto = proyectoService.obtenerProyecto(id).get();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename= informe_0" + proyecto.getId() +".pdf");


        double contadorItem = 0;
        double contadorAPU = 0;

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        Font verdanaFont = getVerdanaFont(7, Font.NORMAL);
        Font verdanaBFont = getVerdanaFont(7, Font.BOLD);
        document.open();


        PdfPTable table = new PdfPTable(1);


        table.addCell(getCell(proyecto.getNombre(), 1, verdanaBFont, Element.ALIGN_CENTER));

        document.add(table);

        table = new PdfPTable(1);

        table.addCell(getCell("PRESUPUESTO DE OBRA", 1, verdanaBFont, Element.ALIGN_CENTER));
        document.add(table);

        table = new PdfPTable(7);
        table.addCell(getCell("ITEMS", 1, verdanaBFont, Element.ALIGN_CENTER));
        table.addCell(getCell("DESCRIPCIÓN ACTIVIDADES", 1, verdanaBFont, Element.ALIGN_CENTER));
        table.addCell(getCell("UND", 1, verdanaBFont, Element.ALIGN_CENTER));
        table.addCell(getCell("CANT", 1, verdanaBFont, Element.ALIGN_CENTER));
        table.addCell(getCell("VR. UNIT", 1, verdanaBFont, Element.ALIGN_CENTER));
        table.addCell(getCell("VR. PARCIAL", 1, verdanaBFont, Element.ALIGN_CENTER));
        table.addCell(getCell("VR. CAPITULO", 1, verdanaBFont, Element.ALIGN_CENTER));
        document.add(table);

        List<Subproyecto> subproyectos = subproyectoRepository.findByProyectoAndEnabledTrueOrderByNombreAsc(proyecto);
        for (Subproyecto subproyecto : subproyectos) {

        	table = new PdfPTable(1);
        	table.addCell(getCell(subproyecto.getNombre(), 1, verdanaBFont, Element.ALIGN_CENTER));
        	document.add(table);
			List<ItemSubproyecto> itemSubproyectos = itemSubproyectoRepository.findBySubproyectoAndEnabledTrueOrderByItem_NombreAsc(subproyecto);
			contadorItem=0;
			for(ItemSubproyecto itemSubproyecto : itemSubproyectos) {
        		contadorItem++;
        		table = new PdfPTable(7);
        		table.addCell(getCell(contadorItem + "", 1, verdanaBFont, Element.ALIGN_CENTER));
            	table.addCell(getCell(itemSubproyecto.getItem().getNombre(), 1, verdanaBFont, Element.ALIGN_CENTER));
            	table.addCell(getCell("", 1, verdanaFont, Element.ALIGN_CENTER));
            	table.addCell(getCell("", 1, verdanaFont, Element.ALIGN_CENTER));
            	table.addCell(getCell("", 1, verdanaFont, Element.ALIGN_CENTER));
            	table.addCell(getCell("", 1, verdanaFont, Element.ALIGN_CENTER));
            	table.addCell(getCell("$ " + itemSubproyecto.getValorCapitulo(), 1, verdanaBFont, Element.ALIGN_CENTER));
            	document.add(table);
            	List<APUItemSubproyecto> apuItemSubproyectos = apuItemSubproyectoRepository.findByItemSubproyectoAndEnabledTrueOrderByApu_NombreAsc(itemSubproyecto);
				contadorAPU = 0;
				for(APUItemSubproyecto apuItemSubproyecto : apuItemSubproyectos) {
					contadorAPU ++;


            		table = new PdfPTable(7);
            		table.addCell(getCell((int)contadorItem + "." + (int)contadorAPU, 1, verdanaBFont, Element.ALIGN_CENTER));
                	table.addCell(getCell(apuItemSubproyecto.getApu().getNombre(), 1, verdanaFont, Element.ALIGN_CENTER));
                	table.addCell(getCell(apuItemSubproyecto.getApu().getUnidad(), 1, verdanaFont, Element.ALIGN_CENTER));
                	table.addCell(getCell(Math.round(apuItemSubproyecto.getCantidad()*100d)/100d + "", 1, verdanaFont, Element.ALIGN_CENTER));
                	table.addCell(getCell("$ " + apuItemSubproyecto.getValorUnitario(), 1, verdanaFont, Element.ALIGN_CENTER));
                	table.addCell(getCell("$ " + apuItemSubproyecto.getValorParcial(), 1, verdanaFont, Element.ALIGN_CENTER));
                	table.addCell(getCell("", 1, verdanaFont, Element.ALIGN_CENTER));
                	document.add(table);
            	}

        	}

        	table = new PdfPTable(7);
        	table.addCell(getCell("", 1, verdanaFont, Element.ALIGN_CENTER));
        	table.addCell(getCell("SUBTOTAL " + subproyecto.getNombre(), 5, verdanaBFont, Element.ALIGN_LEFT));
        	table.addCell(getCell("$ " + subproyecto.getCosto(), 1, verdanaBFont, Element.ALIGN_CENTER));
        	document.add(table);
        }

        table = new PdfPTable(7);
    	table.addCell(getCell("", 1, verdanaFont, Element.ALIGN_CENTER));
    	table.addCell(getCell("COSTO TOTAL DEL PROYECTO", 5, verdanaBFont, Element.ALIGN_LEFT));
    	table.addCell(getCell("$ " + proyecto.getCosto(), 1, verdanaBFont, Element.ALIGN_CENTER));
    	document.add(table);

        document.close();
    }

    private static PdfPCell getCell(String text, int colspan, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }

    private static Font getVerdanaFont(float size, int style) {
        return FontFactory.getFont("Verdana", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, size, style);
    }
}

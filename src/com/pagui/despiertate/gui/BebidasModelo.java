package com.pagui.despiertate.gui;

import com.pagui.despiertate.base.Bebidas;
import com.pagui.despiertate.base.Cafe;
import com.pagui.despiertate.base.Te;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de la aplicación DespiertaTe.
 * Mantiene la lista de bebidas y gestiona la importación/exportación
 * en formato XML y TXT.
 */
public class BebidasModelo {

    private List<Bebidas> listaBebidas;

    /**
     * Crea un modelo vacío sin bebidas.
     */
    public BebidasModelo() {
        listaBebidas = new ArrayList<Bebidas>();
    }

    /**
     * Devuelve la lista de bebidas gestionadas por el modelo.
     *
     * @return lista de bebidas
     */
    public List<Bebidas> getBebidas() {
        return listaBebidas;
    }

    /**
     * Comprueba si ya existe una bebida con el código indicado.
     *
     * @param codigo código a buscar
     * @return true si existe, false en caso contrario
     */
    public boolean existeCodigo(String codigo) {
        for (Bebidas b : listaBebidas) {
            if (b.getCodigo().equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Da de alta un nuevo café en la lista.
     */
    public void altaCafe(String codigo,
                         String nombre,
                         String origen,
                         double precioKg,
                         LocalDate fechaConsPref,
                         int intensidad,
                         boolean descafeinado) {
        Cafe cafe = new Cafe(codigo, nombre, origen, precioKg, fechaConsPref, intensidad, descafeinado);
        listaBebidas.add(cafe);
    }

    /**
     * Da de alta un nuevo té en la lista.
     */
    public void altaTe(String codigo,
                       String nombre,
                       String origen,
                       double precioKg,
                       LocalDate fechaConsPref,
                       double temperaturaConsumo,
                       int minutosInf) {
        Te te = new Te(codigo, nombre, origen, precioKg, fechaConsPref, temperaturaConsumo, minutosInf);
        listaBebidas.add(te);
    }

    /**
     * Exporta la lista de bebidas a un fichero XML.
     *
     * @param fichero fichero de salida
     */
    public void exportarXML(File fichero) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation dom = builder.getDOMImplementation();
        Document documento = dom.createDocument(null, "Bebidas", null);
        documento.setXmlVersion("1.0");

        Element raiz = documento.getDocumentElement();

        for (Bebidas unaBebida : listaBebidas) {
            Element nodoBebida;
            if (unaBebida instanceof Cafe) {
                nodoBebida = documento.createElement("Cafe");
            } else {
                nodoBebida = documento.createElement("Te");
            }
            raiz.appendChild(nodoBebida);

            Element nodoDatos = documento.createElement("codigo");
            nodoBebida.appendChild(nodoDatos);
            Text texto = documento.createTextNode(unaBebida.getCodigo());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("nombre");
            nodoBebida.appendChild(nodoDatos);
            texto = documento.createTextNode(unaBebida.getNombre());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("origen");
            nodoBebida.appendChild(nodoDatos);
            texto = documento.createTextNode(unaBebida.getOrigen());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("precio-kg");
            nodoBebida.appendChild(nodoDatos);
            texto = documento.createTextNode(String.valueOf(unaBebida.getPrecioKg()));
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("fecha-cons-pref");
            nodoBebida.appendChild(nodoDatos);
            texto = documento.createTextNode(unaBebida.getFechaConsPref().toString());
            nodoDatos.appendChild(texto);

            if (unaBebida instanceof Cafe) {
                Cafe cafe = (Cafe) unaBebida;

                nodoDatos = documento.createElement("intensidad");
                nodoBebida.appendChild(nodoDatos);
                texto = documento.createTextNode(String.valueOf(cafe.getIntensidad()));
                nodoDatos.appendChild(texto);

                nodoDatos = documento.createElement("descafeinado");
                nodoBebida.appendChild(nodoDatos);
                texto = documento.createTextNode(String.valueOf(cafe.isDescafeinado()));
                nodoDatos.appendChild(texto);
            } else if (unaBebida instanceof Te) {
                Te te = (Te) unaBebida;

                nodoDatos = documento.createElement("temperatura-consumo");
                nodoBebida.appendChild(nodoDatos);
                texto = documento.createTextNode(String.valueOf(te.getTemperaturaConsumo()));
                nodoDatos.appendChild(texto);

                nodoDatos = documento.createElement("minutos-inf");
                nodoBebida.appendChild(nodoDatos);
                texto = documento.createTextNode(String.valueOf(te.getMinutosInf()));
                nodoDatos.appendChild(texto);
            }
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(documento);
        StreamResult result = new StreamResult(fichero);
        transformer.transform(source, result);
    }

    /**
     * Importa bebidas desde un fichero XML, sustituyendo la lista actual.
     *
     * @param fichero fichero XML de entrada
     */
    public void importarXML(File fichero) throws ParserConfigurationException, IOException, SAXException {
        listaBebidas.clear();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(fichero);

        Element raiz = documento.getDocumentElement();
        for (int i = 0; i < raiz.getChildNodes().getLength(); i++) {
            if (!(raiz.getChildNodes().item(i) instanceof Element)) {
                continue;
            }
            Element nodoBebida = (Element) raiz.getChildNodes().item(i);
            String tagName = nodoBebida.getTagName();

            String codigo = nodoBebida.getElementsByTagName("codigo").item(0).getTextContent();
            String nombre = nodoBebida.getElementsByTagName("nombre").item(0).getTextContent();
            String origen = nodoBebida.getElementsByTagName("origen").item(0).getTextContent();
            String precioStr = nodoBebida.getElementsByTagName("precio-kg").item(0).getTextContent();
            String fechaStr = nodoBebida.getElementsByTagName("fecha-cons-pref").item(0).getTextContent();

            double precio;
            LocalDate fecha;
            try {
                precio = Double.parseDouble(precioStr.replace(",", "."));
                fecha = LocalDate.parse(fechaStr);
            } catch (Exception ex) {
                continue;
            }

            if (tagName.equals("Cafe")) {
                String intensidadStr = nodoBebida.getElementsByTagName("intensidad").item(0).getTextContent();
                String descafeinadoStr = nodoBebida.getElementsByTagName("descafeinado").item(0).getTextContent();
                try {
                    int intensidad = Integer.parseInt(intensidadStr.trim());
                    boolean descafeinado = Boolean.parseBoolean(descafeinadoStr.trim());
                    Cafe cafe = new Cafe(codigo, nombre, origen, precio, fecha, intensidad, descafeinado);
                    listaBebidas.add(cafe);
                } catch (Exception ex) {
                    continue;
                }
            } else if (tagName.equals("Te")) {
                String tempStr = nodoBebida.getElementsByTagName("temperatura-consumo").item(0).getTextContent();
                String minStr = nodoBebida.getElementsByTagName("minutos-inf").item(0).getTextContent();
                try {
                    double temperatura = Double.parseDouble(tempStr.replace(",", "."));
                    int minutos = Integer.parseInt(minStr.trim());
                    Te te = new Te(codigo, nombre, origen, precio, fecha, temperatura, minutos);
                    listaBebidas.add(te);
                } catch (Exception ex) {
                    continue;
                }
            }
        }
    }

    /**
     * Exporta la lista de bebidas a un fichero de texto plano.
     * Cada línea contiene una bebida en formato separado por ';'.
     *
     * @param fichero fichero de salida
     */
    public void exportarTXT(File fichero) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fichero))) {
            for (Bebidas b : listaBebidas) {
                if (b instanceof Cafe) {
                    Cafe c = (Cafe) b;
                    pw.println("Cafe;" +
                            c.getCodigo() + ";" +
                            c.getNombre() + ";" +
                            c.getOrigen() + ";" +
                            c.getPrecioKg() + ";" +
                            c.getFechaConsPref() + ";" +
                            c.getIntensidad() + ";" +
                            c.isDescafeinado());
                } else if (b instanceof Te) {
                    Te t = (Te) b;
                    pw.println("Te;" +
                            t.getCodigo() + ";" +
                            t.getNombre() + ";" +
                            t.getOrigen() + ";" +
                            t.getPrecioKg() + ";" +
                            t.getFechaConsPref() + ";" +
                            t.getTemperaturaConsumo() + ";" +
                            t.getMinutosInf());
                }
            }
        }
    }

    /**
     * Importa bebidas desde un fichero de texto plano generado por exportarTXT.
     *
     * @param fichero fichero TXT de entrada
     */
    public void importarTXT(File fichero) throws IOException {
        listaBebidas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(";");
                if (partes.length < 8) {
                    continue;
                }
                String tipo = partes[0];
                String codigo = partes[1];
                String nombre = partes[2];
                String origen = partes[3];
                double precio;
                LocalDate fecha;
                try {
                    precio = Double.parseDouble(partes[4].replace(",", "."));
                    fecha = LocalDate.parse(partes[5]);
                } catch (Exception ex) {
                    continue;
                }
                if (tipo.equalsIgnoreCase("Cafe")) {
                    try {
                        int intensidad = Integer.parseInt(partes[6].trim());
                        boolean descafeinado = Boolean.parseBoolean(partes[7].trim());
                        Cafe cafe = new Cafe(codigo, nombre, origen, precio, fecha, intensidad, descafeinado);
                        listaBebidas.add(cafe);
                    } catch (Exception ex) {
                        continue;
                    }
                } else if (tipo.equalsIgnoreCase("Te")) {
                    try {
                        double temperatura = Double.parseDouble(partes[6].replace(",", "."));
                        int minutos = Integer.parseInt(partes[7].trim());
                        Te te = new Te(codigo, nombre, origen, precio, fecha, temperatura, minutos);
                        listaBebidas.add(te);
                    } catch (Exception ex) {
                        continue;
                    }
                }
            }
        }
    }
}

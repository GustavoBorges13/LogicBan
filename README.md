# LogicBan

LogicBan é um jogo 2D desenvolvido em Java como projeto acadêmico. O objetivo do jogo é desafiar os jogadores com quebra-cabeças lógicos envolventes.

## 📥 Instalação

### Requisitos Mínimos
- **Sistema Operacional**: Windows 7/8/10/11, Linux (Debian-based) ou macOS
- **Java Development Kit (JDK)** 23 ou superior  
- **Processador**: Intel Core i3 ou equivalente
- **Memória RAM**: 1GB
- **Espaço em disco**: 600MB livres
- **Requisitos gráficos**: Utiliza a API gráfica nativa do Java (Graphics2D), sem necessidade de uma GPU dedicada.  

### Como Instalar
A versão do LogicBan está disponível nos seguintes formatos:

✅ **Windows**:
- **Instalador** (`LogicBan-Setup.exe`)
- **Executável portátil** (`LogicBan.exe`)

✅ **Linux (Debian-based)**:
- **Pacote .deb** (`LogicBan.deb`)

✅ **macOS**:
- **Pacote .dmg** (`LogicBan.dmg`)

✅ **Alternativa multiplataforma**:
- **Executável Java (JAR)** (`LogicBan.jar`)

### Passos de Instalação

1. Acesse a [página de releases](https://github.com/GustavoBorges13/LogicBan/releases/latest) e baixe a versão correspondente ao seu sistema operacional.
  
2. **Windows:**
   - Baixe o **instalador** (`LogicBan-Setup.exe`) ou o **executável portátil** (`LogicBan.exe`).
   - Se for o instalador, execute o arquivo `.exe` e siga as instruções na tela para completar a instalação.
   - Se for o executável, basta clicar no arquivo `LogicBan.exe` para rodar o jogo diretamente.

3. **Linux (Debian-based):**
   - Baixe o arquivo `.deb` (`LogicBan.deb`).
   - Instale o pacote usando o seguinte comando:
     ```sh
     sudo dpkg -i LogicBan.deb
     ```
   - Caso seja necessário, corrija dependências com:
     ```sh
     sudo apt-get install -f
     ```
   - Depois de instalado, você pode executar o jogo a partir do menu de aplicativos.

4. **macOS:**
   - Baixe o arquivo `.dmg` (`LogicBan.dmg`).
   - Abra o arquivo `.dmg` e arraste o jogo para a pasta **Aplicativos**.
   - Execute o jogo diretamente da pasta **Aplicativos**.

5. **Executável via JAR (multiplataforma):**
   - Certifique-se de que o **Java** está instalado no seu computador. Você pode verificar a versão com o comando:
     ```sh
     java -version
     ```
   - Baixe o arquivo `LogicBan.jar`.
   - Execute o arquivo JAR com o seguinte comando:
     ```sh
     java -jar LogicBan.jar
     ```
   
## 🎮 Como Jogar
Para aprender a jogar, acesse nossa [WIKI](https://github.com/GustavoBorges13/logicban/wiki) onde explicamos o funcionamento do jogo, mecânicas e dicas.

## 🛠 Desenvolvimento
Este jogo foi desenvolvido usando:
- **Java** (Swing para interface gráfica e Graphics2d para graficos)
- **Maven** para gerenciamento de dependências
- **GitHub** para controle de versãoe CI/CD (github actions)

## 📌 Roadmap
- [x] Game Design
- [x] Menu do jogo
- [x] Mecanica do jogo
- [x] Fases
- [x] Interface de icones, animações e efeitos visuais
- [x] Modos de jogo adicionais
- [x] Feedback sonoro
- [x] Port para Linux e macOS
- [x] Creditos
- [x] Design das artes
- [x] Musicas de fundo
- [x] Implementação de salvamento automatico
- [x] Desenvolvimento da historia
- [x] Desenvolvimento de tutorial interativo no jogo
- [ ] Desenvolvimento de tutorial interativo no github
- [ ] Testes e correções
- [ ] Finalização
- [ ] Entrega

## 👥 Colaboradores
Desenvolvido por:
- Gustavo Silva ([GitHub](https://github.com/GustavoBorges13))
- Davi Marques
- Marcos Sousa
- Luís Lopes
- Michael Silva
- Rafael Andrade

## 👥 Inspiração
Este projeto foi inspirado no trabalho desenvolvido pelo [RyiSnow](https://www.youtube.com/@RyiSnow).

## 📄 Licença
Este projeto está licenciado sob a MIT License - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

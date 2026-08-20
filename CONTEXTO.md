# Contexto del proyecto — `tarea00-acumuladores`

> Documento de análisis generado para tener a mano la foto completa del repo.
> Última actualización: 2026-08-19

---

## 1. Qué es este proyecto

Trabajo práctico **Tarea 00 — Acumuladores** de la materia **Programación II (UNGS)**.
Es un fork del repo plantilla de la cátedra (`ungs-programacion-ii/tarea00-acumuladores`)
donde el alumno debe implementar 4 métodos sobre matrices de enteros usando el
**patrón acumulador** (variables booleanas / numéricas que se van acumulando dentro
de bucles anidados, sin `break` ni `return` temprano).

- **Repo del alumno:** https://github.com/Iannmachado/tarea00-acumuladores
- **Rama principal:** `main`
- **Estado actual:** ✅ **implementación completa, los 16 tests pasan**
- El enunciado detallado (con ejemplos de uso) está en **Moodle**, no en el repo.

---

## 2. Estructura de archivos

```
tarea00-acumuladores/
├── .classpath                      # Config Eclipse: src/, bin/, junit.jar, hamcrest-core.jar, JavaSE-1.8
├── .project                        # Proyecto Eclipse (nature: org.eclipse.jdt.core.javanature)
├── .gitignore                      # Ignora *.class y *.jar (por eso lib/ y bin/ NO están versionados)
├── README.md                       # Instrucciones de la cátedra (fork, JUnit, GitHub Actions)
├── CONTEXTO.md                     # <- este documento
├── .github/workflows/java-test.yml # CI: compila y corre los tests en cada push/PR a main
├── .vscode/settings.json           # java.project.referencedLibraries -> lib/**/*.jar
├── lib/                            # NO versionado (gitignored)
│   ├── junit.jar                   # JUnit 4.13.2
│   └── hamcrest-core.jar           # Hamcrest Core 1.3
├── bin/                            # NO versionado — salida de compilación (.class)
└── src/acumuladores/
    ├── Acumuladores.java           # <- la clase a implementar (YA IMPLEMENTADA)
    └── AcumuladoresTest.java       # <- tests de la cátedra (NO tocar)
```

**Archivos versionados en git (8):** `.classpath`, `.gitignore`, `.project`,
`.vscode/settings.json`, `README.md`, `.github/workflows/java-test.yml`,
`src/acumuladores/Acumuladores.java`, `src/acumuladores/AcumuladoresTest.java`.

---

## 3. Stack técnico

| Ítem | Valor |
|---|---|
| Lenguaje | Java |
| JDK local instalado | **24.0.2** |
| JDK declarado en `.classpath` | JavaSE-1.8 |
| JDK usado en CI | **11 (Temurin)** |
| Framework de test | **JUnit 4.13.2** + Hamcrest Core 1.3 |
| Build | Manual (`javac` / `java`) — **sin Maven ni Gradle** |
| IDE objetivo | Eclipse (hay config), VS Code también soportado |
| CI | GitHub Actions (`.github/workflows/java-test.yml`) |

⚠️ Hay tres versiones de Java conviviendo (1.8 en `.classpath`, 11 en CI, 24 local).
No rompe nada porque el código no usa features modernas, pero conviene saberlo.

---

## 4. La clase `Acumuladores` — los 4 ejercicios

Archivo: `src/acumuladores/Acumuladores.java` (117 líneas, sin estado, 4 métodos públicos).
**Todos los métodos siguen el mismo esquema:** guarda de validación al inicio → variable
acumuladora → bucles anidados que recorren TODO (sin corte anticipado) → `return` del acumulador.

### Ej. 1 — `todosMultiplosEnAlgunaFila(int[][] mat, int num) : boolean` (línea 16)

¿Existe **alguna** fila donde **todos** los elementos sean múltiplos de `num`?

- Guarda: `mat.length == 0 || num <= 0` → `false`
- Acumuladores: `existeFila` (OR, "existe") + `todosMultiplos` (AND, "para todo")
- Es el patrón **∃ fila . ∀ elemento**

### Ej. 2 — `hayInterseccionPorFila(int[][] mat1, int[][] mat2) : boolean` (línea 42)

¿**Todas** las filas `i` de `mat1` comparten al menos un valor con la fila `i` de `mat2`?

- Guarda: alguna vacía o **distinta cantidad de filas** → `false`
- Acumuladores: `hayInterseccionEnTodas` (AND) + `hayInterseccionFila` (OR)
- Triple bucle: `i` (fila) × `j` (col de mat1) × `k` (col de mat2)
- Es el patrón **∀ fila . ∃ par igual**

### Ej. 3 — `algunaFilaSumaMasQueLaColumna(int[][] mat, int nColum) : boolean` (línea 71)

¿Existe alguna fila cuya suma sea **estrictamente mayor** que la suma de la columna `nColum`?

- Guarda: `mat.length == 0 || nColum < 0 || nColum >= mat[0].length` → `false`
- Dos pasadas: primero acumula `sumaColumna`, después recorre filas acumulando `sumaFila`
- Acumuladores numéricos + acumulador booleano `existeFilaMayor` (OR)

### Ej. 4 — `hayInterseccionPorColumna(int[][] mat1, int[][] mat2) : boolean` (línea 101)

¿**Todas** las columnas `c` de `mat1` comparten al menos un valor con la columna `c` de `mat2`?

- Guarda: alguna vacía o **distinta cantidad de columnas** (`mat1[0].length != mat2[0].length`) → `false`
- Espejo del ej. 2 pero iterando por columna; permite que las matrices tengan **distinta cantidad de filas**
- Triple bucle: `col` × `i` (filas de mat1) × `k` (filas de mat2)

---

## 5. Los tests — `AcumuladoresTest`

- **16 tests**, 4 por ejercicio, con `@FixMethodOrder(MethodSorters.NAME_ASCENDING)`
  (por eso los nombres arrancan con `ej1_`, `ej2_`, …).
- `@Before setUp()` reconstruye todos los fixtures antes de cada test.
- Convención de nombres: `ejN_condicion_RetornaTrue|False`.

### Fixture principal (`mat`, 3×4)

```
{  1,  9,  6, 31 }
{  9, 12, 18, 18 }   <- todos múltiplos de 3
{ 15, 14,  9, 30 }
```

### Datos por ejercicio

| Ej | Fixtures | Valores clave |
|---|---|---|
| 1 | `numExisteFilaDeMultiplos=3`, `numNoExisteFilaDeMultiplos=5`, `numNegativo=-3` | también prueba `0` |
| 2 | `matConInterseccionPorFila` (3×3), `matSinInterseccionPorFila` (3×3), `matConDistintaCantFilas` (2×4) | prueba simetría: `(a,b)` y `(b,a)` |
| 3 | `columnaSumaMasQueCualquierFila=3`, `columnaSumaMenosQueAlgunaFila=1`, `columnaFueraDeRango=4` | también prueba `-4` |
| 4 | `matConInterseccionPorColumna` (4×4), `matSinInterseccionPorColumna` (4×4), `matConDistintaCantColumnas` (3×3) | prueba simetría |

### Verificación numérica del ej. 3

- Columna 3 = 31+18+30 = **79** → filas suman 47, 57, 68 → ninguna supera → `false` ✅
- Columna 1 = 9+12+14 = **35** → filas 47, 57, 68 → todas superan → `true` ✅

### Cobertura

Cada ejercicio cubre: caso positivo, caso negativo, entrada inválida (matriz vacía) y
parámetro inválido (número no positivo / índice fuera de rango / dimensiones distintas).

---

## 6. Cómo compilar y correr los tests

### Windows / PowerShell (separador de classpath = `;`)

```powershell
javac -cp "lib/junit.jar;lib/hamcrest-core.jar" -d bin src/acumuladores/*.java
java  -cp "bin;lib/junit.jar;lib/hamcrest-core.jar" org.junit.runner.JUnitCore acumuladores.AcumuladoresTest
```

### Linux / macOS (separador = `:`)

```bash
javac -cp "lib/*" -d bin src/acumuladores/*.java
java  -cp "bin:lib/*" org.junit.runner.JUnitCore acumuladores.AcumuladoresTest
```

### Desde Eclipse

Click derecho en `AcumuladoresTest.java` → Run As → JUnit Test.

**Resultado actual (verificado localmente con JDK 24):**

```
JUnit version 4.13.2
................
OK (16 tests)
```

---

## 7. CI — GitHub Actions

Workflow `.github/workflows/java-test.yml`, se dispara en:

- `push` a `main` o `ci/add-github-actions`
- `pull_request` hacia `main`

Pasos: checkout → JDK 11 Temurin → `mkdir -p lib` → **descarga junit 4.13.2 y hamcrest-core 1.3
desde Maven Central con `wget`** (por eso los jars no hacen falta en el repo) →
`javac -cp "lib/*" src/**/*.java -d bin/` → corre `JUnitCore` sobre todas las clases `*Test.class`
encontradas con `find`.

⚠️ **Detalle frágil:** `src/**/*.java` en bash **sin `globstar`** se expande como `src/*/*.java`.
Funciona hoy porque solo hay un nivel de paquete (`src/acumuladores/`), pero se rompería
con paquetes anidados (`src/a/b/`).

---

## 8. Historial de commits

```
76f303d  Trigger workflow 1
4c73dd2  Trigger workflow
264cd38  implementacion metodos acumuladores        <- el trabajo del alumno
b99afb8  Merge PR #1 from ungs-programacion-ii/ci/add-github-actions
e3c8bb0  feat(acumuladores): esqueleto + clase de pruebas (cátedra)
f6406c2  Initial commit
```

Working tree limpio al momento del análisis.

---

## 9. Observaciones y posibles puntos débiles

Ninguna rompe los tests actuales, pero vale tenerlas identificadas:

1. **Fila vacía = "todos múltiplos" (ej. 1).** Con `mat = {{}}`, `todosMultiplos` queda `true`
   vacuamente y el método devuelve `true`. Es lógicamente correcto (∀ sobre conjunto vacío)
   pero puede no ser lo esperado por el enunciado.
2. **Matrices dentadas (jagged) no contempladas.**
   - Ej. 3 valida contra `mat[0].length` pero después accede a `mat[i][nColum]` para toda fila `i`
     → `ArrayIndexOutOfBoundsException` si alguna fila es más corta.
   - Ej. 4 asume que todas las filas tienen la misma longitud que la fila 0.

   Los tests solo usan matrices rectangulares, así que no salta.
3. **`null` no está contemplado.** Cualquier método con `mat == null` lanza `NullPointerException`.
   El enunciado solo pide manejar "matriz vacía".
4. **Sin corte anticipado.** No hay `break` ni `return` temprano: es **intencional**,
   es justamente el punto pedagógico del patrón acumulador. No "optimizar" esto.
5. **Complejidad.** Ej. 1 y 3 son O(f·c); ej. 2 y 4 son O(f·c²) / O(f²·c) por el triple bucle.
   Aceptable y esperado para el TP.
6. **Desalineación de versiones de Java** (1.8 / 11 / 24) — ver sección 3.
7. **`.gitignore` ignora `*.jar`**, así que `lib/` existe local pero no en el repo;
   el CI los descarga. Si alguien clona el repo tiene que bajar los jars a mano.

---

## 10. Reglas de oro para trabajar sobre este repo

- ❌ **No modificar `AcumuladoresTest.java`** — es el contrato de la cátedra.
- ✅ Mantener el estilo acumulador (sin `break`, sin `return` temprano dentro de los bucles).
- ✅ Las guardas de validación van **al principio** del método, antes de cualquier bucle.
- ✅ Correr los tests seguido, no al final de todo (lo dice el README).
- ⚠️ Recordar: pasar los tests **no garantiza** que la implementación sea correcta,
  pero no pasarlos **sí garantiza** que es incorrecta.

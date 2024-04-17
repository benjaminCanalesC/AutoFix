INSERT INTO vehicle_brand (brand) VALUES
                              ('Hyundai'),
                              ('Toyota'),
                              ('Honda'),
                              ('Nissan'),
                              ('Mitsubishi'),
                              ('Tesla'),
                              ('Ford'),
                              ('Chevrolet'),
                              ('Volkswagen'),
                              ('BMW'),
                              ('Audi');


INSERT INTO vehicle_engine (engine) VALUES
                               ('Gasolina'),
                               ('Diesel'),
                               ('Hibrido'),
                               ('Electrico');

INSERT INTO vehicle_type (type) VALUES
                                    ('Sedan'),
                                    ('Hatchback'),
                                    ('SUV'),
                                    ('Pickup'),
                                    ('Furgoneta');

INSERT INTO vehicle (plate, model, mileage, seats, fabrication_year, vehicle_brand, vehicle_engine, vehicle_type) VALUES
-- Vehículo 1: Toyota Camry, Sedán, Híbrido, año 2019
    ('ABCD12', 'Camry', 15000, 5, 2019, 2, 3, 1),
-- Vehículo 2: Ford F-150, Pickup, Gasolina, año 2022
    ('EFJK34', 'F-150', 8000, 5, 2022, 7, 1, 4),

-- Vehículo 3: BMW i3, Hatchback, Eléctrico, año 2021
    ('GHIL56', 'i3', 12000, 4, 2021, 10, 4, 2),

-- Vehículo 4: Nissan Leaf, Hatchback, Eléctrico, año 2020
    ('MNOP78', 'Leaf', 25000, 5, 2020, 4, 4, 2),

-- Vehículo 5: Chevrolet Bolt, SUV, Eléctrico, año 2018
    ('QRST90', 'Bolt', 30000, 5, 2018, 8, 4, 3),

-- Vehículo 6: Honda Civic, Sedán, Gasolina, año 2022 (0-5 años, 0-5000 km)
    ('UVWX01', 'Civic', 3000, 5, 2022, 3, 1, 1),

-- Vehículo 7: Mitsubishi Outlander, SUV, Híbrido, año 2016 (6-10 años, 25001-40000 km)
    ('YZAB23', 'Outlander', 32000, 7, 2016, 5, 3, 3),

-- Vehículo 8: Volkswagen Golf, Hatchback, Diesel, año 2013 (11-15 años, 12001-25000 km)
    ('CDEF45', 'Golf', 20000, 5, 2013, 9, 2, 2),

-- Vehículo 9: Audi A3, Sedán, Electric, año 2020 (0-5 años, 5001-12000 km)
    ('GHIJ67', 'A3', 11000, 5, 2020, 11, 4, 1),

-- Vehículo 10: Tesla Model S, Sedán, Eléctrico, año 2018 (6-10 años, >40000 km)
    ('KLMN89', 'Model S', 45000, 5, 2018, 6, 4, 1),

-- Vehículo 11: Toyota Corolla, Sedán, Gasolina, año 2005 (>16 años, 25001-40000 km)
    ('OPQR01', 'Corolla', 35000, 5, 2005, 2, 1, 1),

-- Vehículo 12: Ford Mustang, Sedán, Gasolina, año 2008 (>16 años, >40000 km)
    ('STUV23', 'Mustang', 42000, 4, 2008, 7, 1, 1),

-- Vehículo 13: Honda CR-V, SUV, Diesel, año 2007 (>16 años, 12001-25000 km)
    ('WXYZ45', 'CR-V', 24000, 5, 2007, 3, 2, 3),

-- Vehículo 14: Chevrolet Silverado, Pickup, Diesel, año 2002 (>16 años, 5001-12000 km)
    ('ABCD67', 'Silverado', 10000, 5, 2002, 8, 2, 4),

-- Vehículo 15: Nissan Xterra, SUV, Gasolina, año 2003 (>16 años, 0-5000 km)
    ('EFGH89', 'Xterra', 4500, 5, 2003, 4, 1, 3);

INSERT INTO repair_type (
    repair_type,
    repair_description,
    gasoline_cost,
    diesel_cost,
    hybrid_cost,
    electric_cost) VALUES
      (
          'Reparaciones del Sistema de Frenos',
          'Incluye el reemplazo de pastillas de freno, discos, tambores, líneas de freno y reparación o reemplazo del cilindro maestro de frenos.',
          120000,
          120000,
          180000,
          220000
      ),
      (
          'Servicio del Sistema de Refrigeración',
          'Reparación o reemplazo de radiadores, bombas de agua, termostatos y mangueras, así como la solución de problemas de sobrecalentamiento.',
          130000,
          130000,
          190000,
          230000
      ),
      (
          'Reparaciones del Motor',
          'Desde reparaciones menores como el reemplazo de bujías y cables, hasta reparaciones mayores como la reconstrucción del motor o la reparación de la junta de la culata.',
          350000,
          450000,
          700000,
          800000
      ),
      (
          'Reparaciones de la Transmisión',
          'Incluyen la reparación o reemplazo de componentes de la transmisión manual o automática, cambios de líquido y solución de problemas de cambios de marcha.',
          210000,
          210000,
          300000,
          300000
      ),
      (
          'Reparación del Sistema Eléctrico',
          'Solución de problemas y reparación de alternadores, arrancadores, baterías y sistemas de cableado, así como la reparación de componentes eléctricos como faros, intermitentes y sistemas de entretenimiento.',
          150000,
          150000,
          200000,
          250000
      ),
      (
          'Reparaciones del Sistema de Escape',
          'Incluye el reemplazo del silenciador, tubos de escape, catalizador y la solución de problemas relacionados con las emisiones.',
          100000,
          120000,
          450000,
          0
      ),
      (
          'Reparación de Neumáticos y Ruedas',
          'Reparación de pinchazos, reemplazo de neumáticos, alineación y balanceo de ruedas.',
          100000,
          100000,
          100000,
          100000
      ),
      (
          'Reparaciones de la Suspensión y la Dirección',
          'Reemplazo de amortiguadores, brazos de control, rótulas y reparación del sistema de dirección asistida.',
          180000,
          180000,
          210000,
          250000
      ),
      (
          'Reparación del Sistema de Aire Acondicionado y Calefacción',
          'Incluye la recarga de refrigerante, reparación o reemplazo del compresor, y solución de problemas del sistema de calefacción.',
          150000,
          150000,
          180000,
          180000
      ),
      (
          'Reparaciones del Sistema de Combustible',
          'Limpieza o reemplazo de inyectores de combustible, reparación o reemplazo de la bomba de combustible y solución de problemas de suministro de combustible.',
          130000,
          140000,
          220000,
          0
      ),
      (
          'Reparación y Reemplazo del Parabrisas y Cristales',
          'Reparación de pequeñas grietas en el parabrisas o reemplazo completo de parabrisas y ventanas dañadas.',
          80000,
          80000,
          80000,
          80000
      );
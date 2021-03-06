  SELECT CONVERT(VARCHAR,  [16_Reserva].[16_Fecha], 111) AS FECHA,
  CONVERT(VARCHAR, DATEPART(hh,  [16_RESERVA].[16_FECHA])) + ':' + 
  RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [16_RESERVA].[16_FECHA])), 2) AS HORA,
 [dbo].[16_Reserva].[15_Id_Cliente], [dbo].[15_Cliente].[15_Id_Cliente],
 [dbo].[15_Cliente].[15_Nombre],
 [dbo].[15_Cliente].[15_Apellido_Pat], [dbo].[15_Cliente].[15_Apellido_Mat],
 [dbo].[16_Reserva].[17_Id_Campania],[dbo].[17_Campania].[17_Id_Campania],
 [dbo].[17_Campania].[34_Id_Tipo_Sesion], [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion],
 [dbo].[34_Tipo_Sesion].[34_Tipo_Sesion],
 [dbo].[17_Campania].[14_Id_Canal_Venta], [dbo].[14_Canal_Venta].[14_Id_Canal_Venta],
 [dbo].[14_Canal_Venta].[14_Canal],
 [dbo].[17_Campania].[17_Precio],
 [dbo].[17_Campania].[17_Posee_CD],
 [dbo].[17_Campania].[17_Cant_Fotos_CD],
 [dbo].[17_Campania].[17_Cant_10x15],
 [dbo].[17_Campania].[17_Cant_15x21],
 [dbo].[17_Campania].[17_Cant_20x30],
 [dbo].[17_Campania].[17_Cant_30x40],
 [dbo].[16_Reserva].[16_Cantidad_Adicionales],
 [dbo].[16_Reserva].[16_Cantidad_Reagendamiento]
 FROM [dbo].[16_Reserva],[dbo].[14_Canal_Venta],[dbo].[17_Campania],[dbo].[15_Cliente],[dbo].[34_Tipo_Sesion]
 WHERE  [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente]
 AND  [dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania]
 AND  [dbo].[17_Campania].[34_Id_Tipo_Sesion] = [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion]
 AND  [dbo].[17_Campania].[14_Id_Canal_Venta] = [dbo].[14_Canal_Venta].[14_Id_Canal_Venta]














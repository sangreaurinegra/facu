/****** Object:  ForeignKey [FK_ArtCants_Articulos]    Script Date: 04/28/2009 19:40:16 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_ArtCants_Articulos]') AND parent_object_id = OBJECT_ID(N'[dbo].[ArtCants]'))
ALTER TABLE [dbo].[ArtCants] DROP CONSTRAINT [FK_ArtCants_Articulos]
GO
/****** Object:  ForeignKey [FK_ArtCants_Carritos]    Script Date: 04/28/2009 19:40:16 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_ArtCants_Carritos]') AND parent_object_id = OBJECT_ID(N'[dbo].[ArtCants]'))
ALTER TABLE [dbo].[ArtCants] DROP CONSTRAINT [FK_ArtCants_Carritos]
GO
/****** Object:  ForeignKey [FK_Articulos_Categorias]    Script Date: 04/28/2009 19:40:16 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Articulos_Categorias]') AND parent_object_id = OBJECT_ID(N'[dbo].[Articulos]'))
ALTER TABLE [dbo].[Articulos] DROP CONSTRAINT [FK_Articulos_Categorias]
GO
/****** Object:  ForeignKey [FK_Categorias_Categorias]    Script Date: 04/28/2009 19:40:16 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Categorias_Categorias]') AND parent_object_id = OBJECT_ID(N'[dbo].[Categorias]'))
ALTER TABLE [dbo].[Categorias] DROP CONSTRAINT [FK_Categorias_Categorias]
GO
/****** Object:  ForeignKey [FK_Clientes_Carritos]    Script Date: 04/28/2009 19:40:16 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Clientes_Carritos]') AND parent_object_id = OBJECT_ID(N'[dbo].[Clientes]'))
ALTER TABLE [dbo].[Clientes] DROP CONSTRAINT [FK_Clientes_Carritos]
GO
/****** Object:  Table [dbo].[Clientes]    Script Date: 04/28/2009 19:40:16 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Clientes]') AND type in (N'U'))
DROP TABLE [dbo].[Clientes]
GO
/****** Object:  Table [dbo].[ArtCants]    Script Date: 04/28/2009 19:40:16 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ArtCants]') AND type in (N'U'))
DROP TABLE [dbo].[ArtCants]
GO
/****** Object:  Table [dbo].[Articulos]    Script Date: 04/28/2009 19:40:16 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Articulos]') AND type in (N'U'))
DROP TABLE [dbo].[Articulos]
GO
/****** Object:  Table [dbo].[Categorias]    Script Date: 04/28/2009 19:40:16 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Categorias]') AND type in (N'U'))
DROP TABLE [dbo].[Categorias]
GO
/****** Object:  Table [dbo].[Usuarios]    Script Date: 04/28/2009 19:40:16 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Usuarios]') AND type in (N'U'))
DROP TABLE [dbo].[Usuarios]
GO
/****** Object:  Table [dbo].[Carritos]    Script Date: 04/28/2009 19:40:16 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Carritos]') AND type in (N'U'))
DROP TABLE [dbo].[Carritos]
GO
/****** Object:  Table [dbo].[CloudItems]    Script Date: 04/28/2009 19:40:16 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CloudItems]') AND type in (N'U'))
DROP TABLE [dbo].[CloudItems]
GO
/****** Object:  Default [DF_ArtCants_cantidad]    Script Date: 04/28/2009 19:40:16 ******/
IF  EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_ArtCants_cantidad]') AND parent_object_id = OBJECT_ID(N'[dbo].[ArtCants]'))
Begin
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_ArtCants_cantidad]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[ArtCants] DROP CONSTRAINT [DF_ArtCants_cantidad]
END


End
GO
/****** Object:  Default [DF_Carritos_montoTotal]    Script Date: 04/28/2009 19:40:16 ******/
IF  EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_Carritos_montoTotal]') AND parent_object_id = OBJECT_ID(N'[dbo].[Carritos]'))
Begin
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Carritos_montoTotal]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Carritos] DROP CONSTRAINT [DF_Carritos_montoTotal]
END


End
GO
/****** Object:  Default [DF_Usuarios_esAdmin]    Script Date: 04/28/2009 19:40:16 ******/
IF  EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_Usuarios_esAdmin]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuarios]'))
Begin
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Usuarios_esAdmin]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Usuarios] DROP CONSTRAINT [DF_Usuarios_esAdmin]
END


End
GO
/****** Object:  Table [dbo].[CloudItems]    Script Date: 04/28/2009 19:40:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CloudItems]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[CloudItems](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](20) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[weight] [int] NOT NULL,
	[url] [nvarchar](max) COLLATE Modern_Spanish_CI_AS NULL,
	[tooltip] [nvarchar](max) COLLATE Modern_Spanish_CI_AS NULL,
 CONSTRAINT [PK_CloudItems] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON)
)
END
GO
/****** Object:  Table [dbo].[Carritos]    Script Date: 04/28/2009 19:40:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Carritos]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Carritos](
	[id] [int] NOT NULL,
	[fechaCompra] [datetime] NULL,
	[montoTotal] [float] NOT NULL,
 CONSTRAINT [PK_Carritos] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON)
)
END
GO
/****** Object:  Table [dbo].[Usuarios]    Script Date: 04/28/2009 19:40:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Usuarios]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Usuarios](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[login] [nchar](10) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[password] [nchar](10) COLLATE Modern_Spanish_CI_AS NULL,
	[esAdmin] [smallint] NOT NULL,
 CONSTRAINT [PK_Usuarios] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON)
)
END
GO
SET IDENTITY_INSERT [dbo].[Usuarios] ON
INSERT [dbo].[Usuarios] ([id], [login], [password], [esAdmin]) VALUES (1, N'maxi      ', NULL, 0)
INSERT [dbo].[Usuarios] ([id], [login], [password], [esAdmin]) VALUES (2, N'admin     ', NULL, 1)
SET IDENTITY_INSERT [dbo].[Usuarios] OFF
/****** Object:  Table [dbo].[Categorias]    Script Date: 04/28/2009 19:40:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Categorias]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Categorias](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[idSuperCategoria] [int] NULL,
 CONSTRAINT [PK_Categorias] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON)
)
END
GO
SET IDENTITY_INSERT [dbo].[Categorias] ON
INSERT [dbo].[Categorias] ([id], [nombre], [idSuperCategoria]) VALUES (1, N'Libros', NULL)
INSERT [dbo].[Categorias] ([id], [nombre], [idSuperCategoria]) VALUES (2, N'Revistas', 1)
INSERT [dbo].[Categorias] ([id], [nombre], [idSuperCategoria]) VALUES (3, N'Comics modificado', 2)
INSERT [dbo].[Categorias] ([id], [nombre], [idSuperCategoria]) VALUES (4, N'Peliculas', NULL)
INSERT [dbo].[Categorias] ([id], [nombre], [idSuperCategoria]) VALUES (6, N'Romanticas', 4)
INSERT [dbo].[Categorias] ([id], [nombre], [idSuperCategoria]) VALUES (8, N'Prueba Caregoria modif', 3)
SET IDENTITY_INSERT [dbo].[Categorias] OFF
/****** Object:  Table [dbo].[Articulos]    Script Date: 04/28/2009 19:40:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Articulos]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Articulos](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[idCategoria] [int] NULL,
 CONSTRAINT [PK_Articulos] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON)
)
END
GO
/****** Object:  Table [dbo].[ArtCants]    Script Date: 04/28/2009 19:40:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ArtCants]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[ArtCants](
	[idCarrito] [int] NOT NULL,
	[idArticulo] [int] NOT NULL,
	[cantidad] [int] NOT NULL,
	[id] [int] NOT NULL,
 CONSTRAINT [PK_ArtCants] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON)
)
END
GO
/****** Object:  Table [dbo].[Clientes]    Script Date: 04/28/2009 19:40:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Clientes]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Clientes](
	[id] [int] NOT NULL,
	[idCarritoActual] [int] NOT NULL,
 CONSTRAINT [PK_Clientes] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON)
)
END
GO
/****** Object:  Default [DF_ArtCants_cantidad]    Script Date: 04/28/2009 19:40:16 ******/
IF Not EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_ArtCants_cantidad]') AND parent_object_id = OBJECT_ID(N'[dbo].[ArtCants]'))
Begin
IF NOT EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_ArtCants_cantidad]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[ArtCants] ADD  CONSTRAINT [DF_ArtCants_cantidad]  DEFAULT ((0)) FOR [cantidad]
END


End
GO
/****** Object:  Default [DF_Carritos_montoTotal]    Script Date: 04/28/2009 19:40:16 ******/
IF Not EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_Carritos_montoTotal]') AND parent_object_id = OBJECT_ID(N'[dbo].[Carritos]'))
Begin
IF NOT EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Carritos_montoTotal]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Carritos] ADD  CONSTRAINT [DF_Carritos_montoTotal]  DEFAULT ((0)) FOR [montoTotal]
END


End
GO
/****** Object:  Default [DF_Usuarios_esAdmin]    Script Date: 04/28/2009 19:40:16 ******/
IF Not EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_Usuarios_esAdmin]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuarios]'))
Begin
IF NOT EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Usuarios_esAdmin]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Usuarios] ADD  CONSTRAINT [DF_Usuarios_esAdmin]  DEFAULT ((0)) FOR [esAdmin]
END


End
GO
/****** Object:  ForeignKey [FK_ArtCants_Articulos]    Script Date: 04/28/2009 19:40:16 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_ArtCants_Articulos]') AND parent_object_id = OBJECT_ID(N'[dbo].[ArtCants]'))
ALTER TABLE [dbo].[ArtCants]  WITH CHECK ADD  CONSTRAINT [FK_ArtCants_Articulos] FOREIGN KEY([idArticulo])
REFERENCES [dbo].[Articulos] ([id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_ArtCants_Articulos]') AND parent_object_id = OBJECT_ID(N'[dbo].[ArtCants]'))
ALTER TABLE [dbo].[ArtCants] CHECK CONSTRAINT [FK_ArtCants_Articulos]
GO
/****** Object:  ForeignKey [FK_ArtCants_Carritos]    Script Date: 04/28/2009 19:40:16 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_ArtCants_Carritos]') AND parent_object_id = OBJECT_ID(N'[dbo].[ArtCants]'))
ALTER TABLE [dbo].[ArtCants]  WITH CHECK ADD  CONSTRAINT [FK_ArtCants_Carritos] FOREIGN KEY([idCarrito])
REFERENCES [dbo].[Carritos] ([id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_ArtCants_Carritos]') AND parent_object_id = OBJECT_ID(N'[dbo].[ArtCants]'))
ALTER TABLE [dbo].[ArtCants] CHECK CONSTRAINT [FK_ArtCants_Carritos]
GO
/****** Object:  ForeignKey [FK_Articulos_Categorias]    Script Date: 04/28/2009 19:40:16 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Articulos_Categorias]') AND parent_object_id = OBJECT_ID(N'[dbo].[Articulos]'))
ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_Articulos_Categorias] FOREIGN KEY([idCategoria])
REFERENCES [dbo].[Categorias] ([id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Articulos_Categorias]') AND parent_object_id = OBJECT_ID(N'[dbo].[Articulos]'))
ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_Articulos_Categorias]
GO
/****** Object:  ForeignKey [FK_Categorias_Categorias]    Script Date: 04/28/2009 19:40:16 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Categorias_Categorias]') AND parent_object_id = OBJECT_ID(N'[dbo].[Categorias]'))
ALTER TABLE [dbo].[Categorias]  WITH CHECK ADD  CONSTRAINT [FK_Categorias_Categorias] FOREIGN KEY([idSuperCategoria])
REFERENCES [dbo].[Categorias] ([id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Categorias_Categorias]') AND parent_object_id = OBJECT_ID(N'[dbo].[Categorias]'))
ALTER TABLE [dbo].[Categorias] CHECK CONSTRAINT [FK_Categorias_Categorias]
GO
/****** Object:  ForeignKey [FK_Clientes_Carritos]    Script Date: 04/28/2009 19:40:16 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Clientes_Carritos]') AND parent_object_id = OBJECT_ID(N'[dbo].[Clientes]'))
ALTER TABLE [dbo].[Clientes]  WITH CHECK ADD  CONSTRAINT [FK_Clientes_Carritos] FOREIGN KEY([idCarritoActual])
REFERENCES [dbo].[Carritos] ([id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Clientes_Carritos]') AND parent_object_id = OBJECT_ID(N'[dbo].[Clientes]'))
ALTER TABLE [dbo].[Clientes] CHECK CONSTRAINT [FK_Clientes_Carritos]
GO

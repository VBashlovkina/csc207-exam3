# A quick-and-dirty Makefile for people who use Markdown.

# +----------+--------------------------------------------------------
# | Settings |
# +----------+

# These are the applications we use.  Reconfigure as appropriate for
# your machine.

MARKDOWN=/home/rebelsky/bin/Markdown.pl
MARKDOWN=Markdown.pl

HTML2PS=/home/rebelsky/bin/html2ps
HTML2PS=html2ps

PS2PDF=ps2pdf

# +---------------+---------------------------------------------------
# | Generic Rules |
# +---------------+

%.html: %.md
	$(MARKDOWN) $< > $@

%.ps: %.html
	$(HTML2PS) -D -o $@ $<

%.pdf: %.ps
	$(PS2PDF) $< $@
